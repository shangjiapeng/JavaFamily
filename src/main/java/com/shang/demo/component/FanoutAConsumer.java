package com.shang.demo.component;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>FanoutA() 队列消费者</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-12 15:05
 */
@Component
@RabbitListener(queues = "fanout.a")
public class FanoutAConsumer {
    /**
     * 消费消息
     * @RabbitHandler 代表此方法为接收到消息后的处理方法
     */
    @RabbitHandler
    public void receiveFanoutA(String msg){
        System.out.println("[fanout.a] received message: "+msg);
    }
}
