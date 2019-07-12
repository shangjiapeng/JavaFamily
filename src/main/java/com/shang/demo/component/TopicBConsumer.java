package com.shang.demo.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>TopicA消息队列消费者</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-12 18:36
 */
@Component
@RabbitListener(queues = "topic.b")
public class TopicBConsumer {
    /**
     * 消息消费
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void receiveTopicB(String msg) {
        System.out.println("[topic.b] received message:" + msg);
    }
}
