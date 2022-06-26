package com.zirconlabz.ordermanager.choreography;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitBeanConfig {
    public static final String SINGLE_ORDER_TOPIC_EXCH_NAME = "single-orders-exchange";
    public static final String SINGLE_ORDER_QUEUE_NAME = "single-order-queue";
    public static final String SINGLE_ORDER_ROUTING_KEY = "orders.create.*";

    @Bean
    Queue singleOrderqueue() {
        return new Queue(SINGLE_ORDER_QUEUE_NAME, false);
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
    Binding singleOrderBinding(Queue singleOrderqueue, TopicExchange singleOrderExchange) {
        return BindingBuilder.bind(singleOrderqueue).to(singleOrderExchange).with(SINGLE_ORDER_ROUTING_KEY);
    }

    @Bean
    Binding orderUpdateBinding(Queue orderUpdateQueue, TopicExchange singleOrderExchange) {
        return BindingBuilder.bind(orderUpdateQueue).to(singleOrderExchange).with("orders.update");
    }

//    @Bean
//    MessageListenerAdapter orderUpdateListenerAdapter(OrderUpdateListener orderUpdateListener) {
//        return new MessageListenerAdapter(orderUpdateListener, "onOrderUpdate");
//    }

//    @Bean
//    SimpleMessageListenerContainer singleOrderMsgListenerContainer(ConnectionFactory connectionFactory,
//                                                                   @Qualifier("orderUpdateListenerAdapter") MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.addQueueNames("order-update");
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
