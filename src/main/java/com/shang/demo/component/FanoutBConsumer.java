package com.shang.demo.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>FanoutB() 队列消费者</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-12 15:05
 */
@Component
@RabbitListener(queues = "fanout.b")
public class FanoutBConsumer {
    /**
     * 消费消息
     * @RabbitHandler 代表此方法为接收到消息后的处理方法
     */
    @RabbitHandler
    public void receiveFanoutB(String msg){
        System.out.println("[fanout.b] received message: "+msg);
    }
}
