package com.shang.demo.spring;

/**
 * SpringIOC 原理
 * 通过配置文件描述Bean与Bean之间的依赖关系,利用java语言的反射功能实例化Bean
 * 还提供了:Bean实例缓存,声明周期管理,Bean实例代理,事件发布,资源装载等高级服务
 * 步骤:
 *  1 读取bean配置信息(XML,@Configuration,@AutoWired)
 *  2 Spring容器,生成Bean定义注册表
 *  3 根据注册表实例化Bean
 *  4 将Bean实例放到spring容器中(缓存池)
 */
public class SpringIOC {


    /**
     * 上下文对象:
     * ApplicationContext: 面向开发应用由BeanFactory派生而来
     * ClassPathXmlApplicationContext: 默认从类路径加载配置文件
     * FileSystemXmlApplicationContext: 默认从文件系统中装载配置文件
     * ConfigurableApplicationContext: 增加了refresh()和close()方法,让ApplicationContext具有启动,刷新,和关闭应用上下文的能力
     */

    /**
     * SpringBean作用域:
     *  singleton(单例): Spring IoC 容器中只会存在一个共享的 Bean 实例，无论有多少个 Bean 引用它，始终指向同一对象
     *  prototype: 原型模式每次使用时创建一个
     *  request: 一次request创建一个实例,且bean 仅在当前 Http Request 内有效
     *  session: 同request相同，每一次session请求创建新的实例，而不同的实例之间不共享属性，且实例仅在自己的session请求内有效。
     *  global session: 在一个全局的 Http Session 中，容器会返回该 Bean 的同一个实例，仅在使用 portlet context 时有效。
     *  根据经验,对有状态的bean使用prototype作用域,而对于无状态的Bean使用singleton作用域
     */

    /**
     * SpringBean的生命周期:
     * 1,实例化
     * 2,设置属性
     * 3,执行setBeanName()方法
     * 4,执行setApplicationContext()方法
     * 5,执行postProcessBeforeInitialization()方法
     * 6,执行afterPropertiesSet()方法
     * 7,执行我们自定义的Bean初始化方法
     * 8,执行postProcessAfterInitialization()方法
     * 9,使用bean
     * 10,执行destory
     * 11,调用定制的销毁方法
     * Bean标签有两个重要的属性:(init-method和destroy-method)可以用来自定义初始化和注销的方法
     * 也有响应的@PsotConstruct 和 @PreDestroy注解:
     */
}
