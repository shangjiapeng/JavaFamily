package com.shang.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * <p>AOP 功能使用</p>
 *
 * @Author: ShangJiaPeng
 * @Date: 2019/10/30 10:49
 */
@Aspect
@Component
public class SpringBootAspect {

    /**
     * 定义一个切入点
     */
    @Pointcut(value = "execution(* com.shang.demo.util.*.*(..))")
    public void point(){}

    /**
     * 定义一个前置
     */
    @Before("point()")
    public void aopBefore(){
        System.out.println("前置通知: 准备调用一个工具类");
    }

    /**
     * 定义一个前置
     */
    @Before("point()")
    public void aopAfter(){
        System.out.println("后置通知: 调用一个工具类结束");
    }

    /**
     * 处理未处理的java异常
     */
    @AfterThrowing(pointcut ="point()",throwing = "e")
    public void exception(Exception e){
        System.out.println("异常通知"+e);
    }

    /**
     * 环绕通知
     */
    @Around("point()")
    public void aopAround(ProceedingJoinPoint joinPoint)throws Throwable{
        System.out.println("环绕通知 before");
        joinPoint.proceed();
        System.out.println("环绕通知 after");
    }



}
