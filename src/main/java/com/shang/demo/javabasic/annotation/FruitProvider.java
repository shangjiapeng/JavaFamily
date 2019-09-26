package com.shang.demo.javabasic.annotation;

import java.lang.annotation.*;

/**
 * 自定义的"水果提供商"注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FruitProvider {

    //供应商的ID,默认-1
    public int id() default -1;
    //名称,默认空字符串
    public String name() default "";
    //地址
    public String address() default "";
}
