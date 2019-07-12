package com.shang.demo.component;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>编写一个消息发布者，并编写一个发送方法，通过AmqpTemplate往"demoQueue"发送消息</p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-11 15:25
 */
@Component
public class RabbitProducer {

    //Amqp: Advanced message Queue Protocol
    @Resource
    private AmqpTemplate rabbitTemplate;

    /**
     * 产生并且发送queue消息
     */
    public void sendDemoQueue(){
        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-mm-DD hh:MM:ss").format(date);
        System.out.println("[demoQueue] send msg:"+dateString);
        //key值 就是RabbitMqConfig配置文件中配置的队列的名称
        this.rabbitTemplate.convertAndSend("demoQueue",dateString);
    }

    /**
     * 产生并想fanout队列发送消息
     */
    public void sendFanout(){
        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-mm-DD hh:MM:ss").format(date);
        System.out.println("[fanout] send msg:"+dateString);
        //注意:第一个参数是我们交换机的名称,第二个参数是routingKey 不用的时候可以空着,第三个参数是你要发送的消息
        this.rabbitTemplate.convertAndSend("fanoutExchange","",dateString);
    }

    /*
    topicA的key为topic.msg 那么他只会接收包含topic.msg的消息
    topicB的key为topic.#那么他只会接收topic开头的消息
    topicC的key为topic.*.z那么他只会接收topic.x.z这样格式的消息*/
    /**
     * 分别根据匹配规则发送到A\B，B，B\C队列
     */
    public void sendTopic1(){
        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-mm-DD hh:MM:ss").format(date);
        dateString = "[topic.msg] send msg:" + dateString;
        System.out.println(dateString);
        //注意:第一个参数是我们交换机的名称,第二个参数是routingKey 不能为空,第三个参数是你要发送的消息
        //这条消息会被topic.a topic.b 接收到
        this.rabbitTemplate.convertAndSend("topicExchange","topic.msg",dateString);
    }
    public void sendTopic2(){
        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-mm-DD hh:MM:ss").format(date);
        dateString = "[topic.good.msg] send msg:" + dateString;
        System.out.println(dateString);
        //注意:第一个参数是我们交换机的名称,第二个参数是routingKey 不能为空,第三个参数是你要发送的消息
        // 这条信息将会被topic.b接收
        this.rabbitTemplate.convertAndSend("topicExchange","topic.good.msg",dateString);
    }
    public void sendTopic3(){
        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-mm-DD hh:MM:ss").format(date);
        dateString ="[topic.msg.z] send msg:"+dateString;
        System.out.println(dateString);
        //注意:第一个参数是我们交换机的名称,第二个参数是routingKey 不能为空,第三个参数是你要发送的消息
        // 这条信息将会被topic.b、topic.c接收
        this.rabbitTemplate.convertAndSend("topicExchange","topic.msg.z",dateString);
    }
}
