package com.zirconlabz.ordermanager.choreography;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderUpdateListener {

    @Autowired
    Queue orderUpdateQueue;

    @RabbitListener(queues = "#{orderUpdateQueue.getName()}")
    public void onOrderUpdate(SingleOrder order) {
        System.out.println("ORDER_SERVICE|"+order.id+"|ORDER STATUS UPDATED: "+order);
    }
}
