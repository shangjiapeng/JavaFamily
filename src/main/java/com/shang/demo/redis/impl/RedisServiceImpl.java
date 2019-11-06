package com.shang.demo.redis.impl;

import com.shang.demo.enums.LikedStatusEnum;
import com.shang.demo.pojo.UserLike;
import com.shang.demo.pojo.dto.LikedCountDTO;
import com.shang.demo.redis.LikedService;
import com.shang.demo.redis.RedisService;
import com.shang.demo.util.RedisKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/11/6 11:53
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private LikedService likedService;

    @Override
    public void saveLiked2Redis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
    }

    @Override
    public void incrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY__USER_LIKED_COUNT,likedUserId,1);
    }

    @Override
    public void decrementLikedCount(String likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY__USER_LIKED_COUNT,likedUserId,-1);
    }

    @Override
    public List<UserLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike>likeList =new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key= (String) entry.getKey();
            //分离处 likedUserId 和 likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedPostId = split[0];
            Integer value = (Integer) entry.getValue();

            //组装称 UserLike 对象
            UserLike userLike = new UserLike(likedUserId, likedPostId, value);
            likeList.add(userLike);
            //存到 list后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);

        }
        return likeList;
    }

    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY__USER_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO>dtoList =new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            //讲点赞的属性存储在LikedCountDTO中
            String key = (String) entry.getKey();
            LikedCountDTO dto =new LikedCountDTO(key,(Integer)entry.getValue());
            dtoList.add(dto);
            //从 redis 中删除点赞计数
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY__USER_LIKED_COUNT,key);
        }
        return dtoList;
    }
}
