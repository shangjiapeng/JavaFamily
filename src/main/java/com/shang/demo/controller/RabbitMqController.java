package com.shang.demo.controller;

import com.shang.demo.component.RabbitProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p></p>
 *
 * @Author: ShangJiaPeng
 * @Since: 2019-07-11 15:42
 */
@RestController()
@RequestMapping("/amqp")
public class RabbitMqController {

    @Resource
    private RabbitProducer rabbitProducer;

    /**
     * 发送普通的消息
     * @return String
     */
    @GetMapping("/sendDemoQueue")
    public Object sendDemoQueue() {
        rabbitProducer.sendDemoQueue();
        return "success";
    }
    /**
     * 向fanout队列发送消息
     */
    @GetMapping("/sendFanout")
    public Object sendFanout(){
        rabbitProducer.sendFanout();
        return "success";
    }

    /**
     * 向三个topic消息队列中发消息
     */
    @GetMapping("/sendTopicTopicAB")
    public Object sendTopicTopicAB() {
        rabbitProducer.sendTopic1();
        return "success";
    }
    @GetMapping("/sendTopicTopicB")
    public Object sendTopicTopicB() {
        rabbitProducer.sendTopic2();
        return "success";
    }
    @GetMapping("/sendTopicTopicBC")
    public Object sendTopicTopicBC() {
        rabbitProducer.sendTopic3();
        return "success";
    }
}
