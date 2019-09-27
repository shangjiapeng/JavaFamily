package com.shang.demo.log;

/**
 * 有关日志框架的相关信息
 * slf4j: simple logging facade for java 作为日志输出的统一接口
 *
 */
public class LogTip {

    /**
     * lof4j: 可以控制日志信息输送的目的地是控制台
     * 三个组成部分
     * Logger: 控制要启动或禁用哪些日志记录语句,并对日志信息进行级别限制
     * Appenders: 指定了日志将打印到控制台还是文件中
     * Layout: 控制日志信息的显示格式
     * 日志信息的5中级别: DEBUG, INFO, WARN, ERROR, FATAL
     * 只有级别高过配置中规定的级别的信息才能输出,可以方便配置不同情况下要输出的内容
     */

    /**
     * Logback: 被认为是log4j的继承人
     * 三个模块:
     * logback-core: 其他模块的基础设施,提供一些关键的通用机制
     * logback-classic: 地位等同于log4J ,实现了简单的log4j门面
     * logback-access: 与servlet容器交互的模块,
     *
     * 支持Prudent模式,使多个JVM进程能记录同一个日志文件
     * 支持配置文件中加入条件判断,来适应不同的环境
     */

    /**
     * ELK: 是软件 ElasticSearch, LogStash, Kibana 的简称,主要用来搭建大规模日志实时处理系统
     * ElasticSearch: 只要负责将日志索引并存储起来,方 便业务检索查询
     * LogStash: 是日志手机,过滤,转发的中间件
     * Kibana: 日志数据的可视化工具,(饼图,直方图,区域图等)
     */

}
