package com.shang.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>新建一个RabbitMQ配置类，并添加一个demoQueue队列</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-11 15:18
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 定义demoQueue队列
     *
     * @return Queue
     */
    @Bean
    public Queue demoString() {
        return new Queue("demoQueue");
    }

    //=================== fanout广播模式 ====================

    /**
     * 定义3个fanout消息队列
     * @return Queue
     */
    @Bean
    public Queue fanoutA() {
        return new Queue("fanout.a");
    }

    @Bean
    public Queue fanoutB() {
        return new Queue("fanout.b");
    }

    @Bean
    public Queue fanoutC() {
        return new Queue("fanout.c");
    }

    /**
     * 定义fanout 交换器
     */
    @Bean
    FanoutExchange fanoutExchange() {
        //定义一个名为:fanoutExchange的交换器
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 将定义的fanoutA 队列与fanoutExchange交换器绑定
     */
    @Bean
    public Binding bindingExchangeWithA() {
        return BindingBuilder.bind(fanoutA()).to(fanoutExchange());
    }

    /**
     * 将定义的fanoutB 队列与fanoutExchange交换器绑定
     */
    @Bean
    public Binding bindingExchangeWithB() {
        return BindingBuilder.bind(fanoutB()).to(fanoutExchange());
    }

    /**
     * 将定义的fanoutC 队列与fanoutExchange交换器绑定
     */
    @Bean
    public Binding bindingExchangeWithC() {
        return BindingBuilder.bind(fanoutC()).to(fanoutExchange());
    }
    //=================== topic主题模式 ====================
    /**
     * 定义3个topic消息队列
     * @return Queue
     */
    @Bean
    public Queue topicA() {
        return new Queue("topic.a");
    }

    @Bean
    public Queue topicB() {
        return new Queue("topic.b");
    }

    @Bean
    public Queue topicC() {
        return new Queue("topic.c");
    }
    /**
     * 定义 topic 交换器
     */
    @Bean
    TopicExchange topicExchange() {
        //定义一个名为:topicExchange的交换器
        return new TopicExchange("topicExchange");
    }

    /**
     * 将定义的topicA队列与topicExchange交换机绑定
     * @return Binding
     */
    @Bean
    public Binding bindingTopicExchangeWithA() {
        return BindingBuilder.bind(topicA()).to(topicExchange()).with("topic.msg");
    }/**
     * 将定义的topicB队列与topicExchange交换机绑定
     * @return Binding
     */
    @Bean
    public Binding bindingTopicExchangeWithB() {
        return BindingBuilder.bind(topicB()).to(topicExchange()).with("topic.#");
    }/**
     * 将定义的topicC队列与topicExchange交换机绑定
     * @return Binding
     */
    @Bean
    public Binding bindingTopicExchangeWithC() {
        return BindingBuilder.bind(topicC()).to(topicExchange()).with("topic.*.z");
    }
    /*上述配置中：
    topicA的key为topic.msg 那么他只会接收包含topic.msg的消息
    topicB的key为topic.#那么他只会接收topic开头的消息
    topicC的key为topic.*.z那么他只会接收topic.x.z这样格式的消息*/


}
