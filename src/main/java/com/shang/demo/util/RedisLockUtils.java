package com.shang.demo.util;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-02 14:49
 * @Version 1.0
 */
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import javax.annotation.Resource;

/**
 * redis锁工具
 */
@Component
public class RedisLockUtils {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static final String SET_IF_NOT_EXIST = "NX"; // key为空时设置
    //	private static String SET_WITH_EXPIRE_TIME = "PX"; // 毫秒
    private static final String SET_WITH_EXPIRE_TIME = "EX"; // 秒
    //使用lua脚本删除redis中匹配value的key
    private static final  String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


    private final Logger logger = LoggerFactory.getLogger(RedisLockUtils.class);


    public boolean setLock(String key, String value, long expire) {
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.set(key, value, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expire);
            };
            String result = redisTemplate.execute(callback);
            // 设置成功为OK,否则为null
            return StringUtil.isNotBlank(result);
        } catch (Exception e) {
            logger.error("redis加锁失败,key值：" + key, e);
        }
        return false;
    }

    public String get(String key) {
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.get(key);
            };
            String result = redisTemplate.execute(callback);
            return result;
        } catch (Exception e) {
            logger.error("redis获取值失败,key值：" + key, e);
        }
        return "";
    }

    public boolean releaseLock(String key,String value) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> values = new ArrayList<>();
            values.add(value);

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            RedisCallback<Long> callback = (connection) -> {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(SCRIPT, keys, values);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(SCRIPT, keys, values);
                }
                return 0L;
            };
            Long result = redisTemplate.execute(callback);

            return result != null && result >= 0;
        } catch (Exception e) {
            logger.error("redis解锁失败,key值：" + key, e);
        }
        return false;
    }

    public boolean releaseLockList(List<String> keys,List<String> values) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本
            RedisCallback<Long> callback = (connection) -> {
                Object nativeConnection = connection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(SCRIPT, keys, values);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(SCRIPT, keys, values);
                }
                return 0L;
            };
            Long result = redisTemplate.execute(callback);

            return result != null && result > 0;
        } catch (Exception e) {
            logger.error("redis解锁失败,key值：" + JSON.toJSONString(keys), e);
        }
        return false;
    }

}

