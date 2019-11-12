package com.shang.demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Springboot项目的接口防刷-注解</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/11/11 09:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    int seconds();  //时间秒
    int maxCount();  //最大请求次数
    boolean needLogin() default true;   //是否需要登录

}
