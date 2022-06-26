package com.zirconlabz.ordermanager.choreography;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/single-order")
public class SingleOrderResource {

    @Autowired(required = false)
    MessageConverter messageConverter;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping()
    public SingleOrder createSingleOrder(@RequestParam String item, @RequestParam int qty) {
        int id  = ThreadLocalRandom.current().nextInt(100, 500 + 1);
        SingleOrder order = new SingleOrder(id, item, qty);
        System.out.println("ORDER_SERVICE|NEW ORDER:"+order);
        rabbitTemplate.convertAndSend("orders.create.new", order);
        return order;
    }

}
