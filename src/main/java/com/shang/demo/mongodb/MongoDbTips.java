package com.shang.demo.mongodb;


/**
 * mongoDB相关的知识点总结
 *
 */
public class MongoDbTips {


    /**
     * MongoDB 室友是由C++编写的,是一个基于分布式文件存储的开源的数据库系统,
     * 在高负载的情况下,添加更多的节点,可以保证服务器的性能
     * MongoDB 将数据存储为一个文档,数据结构由Key,Value组成,
     * MongoDB 文档了类似于JSON对象,字段值可以包含其他的文档,数组以及文档数组.
     * 你可以在MongoDB中记录中设置任何类型的索引,可以进行分片
     */

    /**
     * MongoDB支持丰富的查询表达式
     * 使用Update()命令可以事项替换完整的文档数据 或者部分指定的数据字段
     * Map/reduce 主要是用来对数据进行批量处理和聚合操作
     * GridFS是MongoDB内置的一个功能,可以用于存放大量小文件
     * 允许在服务端执行脚本,可以用javascript编写某个函数,直接在服务端执行;也可以把函数存储在服务端,下次直接调用即可
     */
}
