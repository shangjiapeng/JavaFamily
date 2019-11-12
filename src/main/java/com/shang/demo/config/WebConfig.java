package com.shang.demo.config;

import com.shang.demo.Interceptor.FangshuaInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p>把Interceptor注册到springboot中</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/11/11 11:31
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private FangshuaInterceptor fangshuaInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fangshuaInterceptor);
    }
}
