package com.shang.demo.annotation;

import java.lang.annotation.*;

/**
 * @Author: 尚家朋
 * @Date: 2019-07-02 14:32
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface NoRepeatSubmit {
    //被锁的数据的id
    String key() default "";

    //唤醒时间
    long acquireTimeout() default 6000L;

    //超时时间
    int lockTime() default 6000;
}
