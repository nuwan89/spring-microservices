package com.zirconlabz.accountmanager.choreography;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This may be included in the Inventory microservice.
 * Here just for demo: https://dev.to/thilanka/choreography-based-saga-pattern-spring-boot-implementation-2c9p-temp-slug-9549230
 *
 */
@Component
public class PaymentReceivedListener {

    @Autowired
    Queue paymentQueue;

    @Autowired
    RabbitTemplate template;

    @RabbitListener(queues = "#{paymentQueue.getName()}")
    public void reserveInventory(SingleOrder order) {
        System.out.println("INVENTORY_SERVICE|"+order.id+"|VERIFIED PAYMENT");
        if (order.qty > 5) {
            System.out.println("INVENTORY_SERVICE|"+order.id+"|STOCKS NOT AVAILABLE");
            order.isFailed = true;
            order.failureReason = "STOCKS NOT AVAILABLE: " + order.qty;
            template.convertAndSend("orders.create.failed", order);
            template.convertAndSend("orders.update", order);
        } else {
            System.out.println("INVENTORY_SERVICE|"+order.id+"|STOCKS RESERVED: QTY" + order.qty);
            template.convertAndSend("orders.update", order);
        }
    }
}
