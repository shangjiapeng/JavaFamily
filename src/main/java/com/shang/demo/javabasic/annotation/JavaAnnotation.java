package com.shang.demo.javabasic.annotation;

/**
 * JAVA 注解相关知识点
 * 自定义注解时需要用到
 *
 * Annotation(注解)是一个接口,程序可以通过反射来获取指定程序中元素的Annotation对象,
 * 然后获取注解中的元数据信息
 * 4种标准元注解:
 * @Terget: 表示修饰的对象范围
 * (Constructor:构造器的声明,Field:域声明包括enum实例,Local_Variable:局部变量声明,
 * Method:方法声明,package:包声明,parameter:参数声明,type:类,接口包括注解类型或enum声明)
 * @Retention: 定义被保留的时间长短,用于描述注解的生命周期(即有效的范围)-->
 * (Source:在源文件中有效,注释将被编译器丢掉 ;Class:注释在Class文件中有效,但是会被jvm丢掉 ;Runtime:在运行时有效,因此可以通过反射机制读取注释的信息)
 * @Document: 将注释包含咋javaDoc中
 * @Inherited: 允许之类继承父类中的注释
 *
 */
public class JavaAnnotation {



}
