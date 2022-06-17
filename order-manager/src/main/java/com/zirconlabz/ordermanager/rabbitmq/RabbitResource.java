package com.zirconlabz.ordermanager.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
public class RabbitResource {

    private Receiver receiver;
    private RabbitTemplate rabbitTemplate;

    public RabbitResource(Receiver receiver, RabbitTemplate rabbitTemplate){
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/send")
    public void sendMessage(@RequestParam("body") String body) {
        System.out.println("Sending msg: "+body);
        rabbitTemplate.convertAndSend(BeanConfig.topicExchangeName,"foo.bar.x", body);
    }
}
