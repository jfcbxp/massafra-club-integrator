package com.massafra.club.integrator.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.massafra.club.integrator.constant.RabbitMq;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
public class RabbitMQConfig  {

    private static final String XDLE = "x-dead-letter-exchange";
    private static final String XDLRK = "x-dead-letter-routing-key";

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        objectMapper.setTimeZone(TimeZone.getTimeZone(ZoneId.of("America/Sao_Paulo")));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public DirectExchange clubExchange() {
        return new DirectExchange(RabbitMq.EXCHANGE_CLUB);
    }

    @Bean
    public DirectExchange clubDeadExchange() {
        return new DirectExchange(RabbitMq.EXCHANGE_DEAD_CLUB);
    }

    //ORDER
    @Bean
    public Queue orderCreateQueue() {
        return QueueBuilder.durable(RabbitMq.CREATE_ORDER_QUEUE)
                .withArgument(XDLE, RabbitMq.EXCHANGE_DEAD_CLUB)
                .withArgument(XDLRK, RabbitMq.CREATE_ORDER_DEAD_ROUTING_KEY)
                .ttl(10000)
                .build();
    }

    @Bean
    public Queue orderCreateQueueDead() {
        return QueueBuilder.durable(RabbitMq.CREATE_ORDER_QUEUE_DEAD).build();
    }

    @Bean
    public Binding orderCreateBinding() {
        return new Binding(RabbitMq.CREATE_ORDER_QUEUE, Binding.DestinationType.QUEUE, RabbitMq.EXCHANGE_CLUB,
                RabbitMq.CREATE_ORDER_ROUTING_KEY, null);
    }

    @Bean
    public Binding orderCreateDeadBinding() {
        return new Binding(RabbitMq.CREATE_ORDER_QUEUE_DEAD, Binding.DestinationType.QUEUE, RabbitMq.EXCHANGE_DEAD_CLUB,
                RabbitMq.CREATE_ORDER_DEAD_ROUTING_KEY, null);
    }

    //CUSTOMER
    @Bean
    public Queue customerCreateQueue() {
        return QueueBuilder.durable(RabbitMq.CREATE_CUSTOMER_QUEUE)
                .withArgument(XDLE, RabbitMq.EXCHANGE_DEAD_CLUB)
                .withArgument(XDLRK, RabbitMq.CREATE_CUSTOMER_ROUTING_KEY)
                .ttl(10000)
                .build();
    }

    @Bean
    public Queue customerCreateQueueDead() {
        return QueueBuilder.durable(RabbitMq.CREATE_CUSTOMER_QUEUE_DEAD).build();
    }

    @Bean
    public Binding customerCreateBinding() {
        return new Binding(RabbitMq.CREATE_CUSTOMER_QUEUE, Binding.DestinationType.QUEUE, RabbitMq.EXCHANGE_CLUB,
                RabbitMq.CREATE_CUSTOMER_ROUTING_KEY, null);
    }

    @Bean
    public Binding customerCreateDeadBinding() {
        return new Binding(RabbitMq.CREATE_CUSTOMER_QUEUE_DEAD, Binding.DestinationType.QUEUE, RabbitMq.EXCHANGE_DEAD_CLUB,
                RabbitMq.CREATE_CUSTOMER_DEAD_ROUTING_KEY, null);
    }


    @Bean
    public Declarables bindings() {
        return new Declarables(
                orderCreateBinding(),
                orderCreateDeadBinding(),
                customerCreateBinding(),
                customerCreateDeadBinding()
        );
    }


}