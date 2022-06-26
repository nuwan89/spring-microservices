package com.zirconlabz.accountmanager.choreography;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

@Configuration
public class RabbitBeanConfig {

    public static final String SINGLE_ORDER_TOPIC_EXCH_NAME = "single-orders-exchange";
    public static final String SINGLE_ORDER_QUEUE_NAME = "single-order-queue";

    @Bean
    Queue createOrderQueue() {
        return new Queue(SINGLE_ORDER_QUEUE_NAME, false); // To be consumed by Payment service
    }

    @Bean
    Queue paymentQueue() {
        return new Queue("payment", false); // Successful order payments end up here to be consumed by Inventory service
    }

    @Bean
    Queue orderUpdateQueue() {
        return new Queue("order-update", false); // Stores the final order outcome
    }

    @Bean
    TopicExchange singleOrderExchange() {
        return new TopicExchange(SINGLE_ORDER_TOPIC_EXCH_NAME);
    }

    @Bean
    Binding paymentBinding(Queue createOrderQueue, TopicExchange singleOrderExchange) {
        return BindingBuilder.bind(createOrderQueue).to(singleOrderExchange).with("orders.create.*");
    }

    @Bean
    Binding inventoryBinding(Queue paymentQueue, TopicExchange singleOrderExchange) {
        return BindingBuilder.bind(paymentQueue).to(singleOrderExchange).with("orders.pay");
    }

    @Bean
    Binding orderUpdateBinding(Queue orderUpdateQueue, TopicExchange singleOrderExchange) {
        return BindingBuilder.bind(orderUpdateQueue).to(singleOrderExchange).with("orders.update");
    }

//    @Bean
//    MessageListenerAdapter orderListener(OrderCreatedListener orderCreatedListener, MessageConverter messageConverter) {
//
//        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(orderCreatedListener, "onOrderCreated");
//        listenerAdapter.setMessageConverter(messageConverter);
//        return listenerAdapter;
//    }

//    @Bean
//    MessageListenerAdapter accountListener(PaymentReceivedListener paymentReceivedListener, MessageConverter messageConverter) {
//        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(paymentReceivedListener, "reserveInventory");
//        listenerAdapter.setMessageConverter(messageConverter);
//        return listenerAdapter;
//    }

//    @Bean
//    SimpleMessageListenerContainer accountListenerContainer(ConnectionFactory connectionFactory,
//                                                            @Qualifier("accountListener") MessageListenerAdapter listenerAdapter){
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
//        simpleMessageListenerContainer.addQueueNames("payment");
//        simpleMessageListenerContainer.setMessageListener(listenerAdapter);
//        return simpleMessageListenerContainer;
//    }

//    @Bean
//    SimpleMessageListenerContainer orderListenerContainer(ConnectionFactory connectionFactory,
//                                                     @Qualifier("orderListener") MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.addQueueNames(SINGLE_ORDER_QUEUE_NAME);
//        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
//        simpleMessageListenerContainer.setMessageListener(listenerAdapter);
//        return simpleMessageListenerContainer;
//    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, final MessageConverter messageConverter)
    {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setExchange(SINGLE_ORDER_TOPIC_EXCH_NAME); //Otherwise default rabbit queue
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }



}
