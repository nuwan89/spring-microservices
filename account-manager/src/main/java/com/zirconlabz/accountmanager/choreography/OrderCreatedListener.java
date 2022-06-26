package com.zirconlabz.accountmanager.choreography;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    @Autowired
    Queue createOrderQueue;

    @Autowired
    RabbitTemplate rabbit;

    @RabbitListener(queues = "#{createOrderQueue.getName()}")
    public void onOrderCreated(SingleOrder order, @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        System.out.println("ACCOUNT_SERVICE|"+order.id+"|RESPONDING TO ORDER CREATED  key: "+key);
        String[] tokens = key.split("\\.");
        if (tokens.length > 1 && tokens[tokens.length - 1].equals("failed")) {
            //Compensating transaction
            System.out.println("ACCOUNT_SERVICE|"+order.id+"|COMPENSATING AMOUNT = +"+order.cost);
        } else {
            if (order.cost > 100) {
                System.out.println("ACCOUNT_SERVICE|"+order.id+"|NOT ENOUGH FUNDS");
                order.isFailed = true;
                order.failureReason = "NOT ENOUGH FUNDS: " + order.cost;
                rabbit.convertAndSend("orders.update", order);
            } else {
                System.out.println("ACCOUNT_SERVICE|"+order.id+"|DEBITED AMOUNT = -"+order.cost);
                rabbit.convertAndSend("orders.pay", order);
            }
        }
    }

}
