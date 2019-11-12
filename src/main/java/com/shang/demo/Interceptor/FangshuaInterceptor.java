package com.shang.demo.Interceptor;

import com.alibaba.fastjson.JSON;
import com.shang.demo.annotation.AccessLimit;
import com.shang.demo.pojo.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * <p>在Interceptor拦截器中实现</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/11/11 09:59
 */
@Component
public class FangshuaInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断请求是否属于方法的请求
        if (handler instanceof HandlerMethod) {

            HandlerMethod hm = (HandlerMethod) handler;

            //获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            String key = request.getRequestURI();
            //如果需要登录
            String username = null;
            if (login) {
                //获取登录用户名称,如:
                username = "shang";
            }

            //从redis中获取用户访问的次数
            Integer count = (Integer) redisTemplate.opsForHash().get(key, username);
            if (count == null) {
                //第一次访问
                redisTemplate.opsForHash().put(key, username, 1);
                redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
            } else if (count < maxCount) {
                //加1
                redisTemplate.opsForHash().increment(key, username, 1L);
            } else {
                //超出访问次数
                giveResponse(response, new JsonResult(101, "超出访问次数!")); //这里的CodeMsg是一个返回参数
                return false;
            }
        }
        return true;
    }

    private void giveResponse(HttpServletResponse response, JsonResult jsonResult) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(jsonResult);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();

    }

}