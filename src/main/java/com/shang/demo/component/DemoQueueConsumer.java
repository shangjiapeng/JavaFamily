package com.shang.demo.component;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 编写一个消息消费者，通过@RabbitListener(queues = "demoQueue")注解监听"demoQueue"队列，
 * 并用@RabbitHandler注解相关方法，这样在在队列收到消息之后，交友@RabbitHandler注解的方法进行处理
 * </p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-11 15:25
 */
@Component
@RabbitListener(queues = "demoQueue")
public class DemoQueueConsumer {


    /**
     * 消息消费
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void receiveDemoQueue(String msg){
        System.out.println("[demoQueue] I have received message:" +msg);
    }

}
