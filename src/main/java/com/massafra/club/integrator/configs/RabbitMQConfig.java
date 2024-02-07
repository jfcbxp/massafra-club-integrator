package com.massafra.club.integrator.configs;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.massafra.club.integrator.constants.RabbitMQ;
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
public class RabbitMQConfig {

    private static final String XDLE = "x-dead-letter-exchange";
    private static final String XDLRK = "x-dead-letter-routing-key";

    @Bean
    public MessageConverter jsonMessageConverter() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        objectMapper.setTimeZone(TimeZone.getTimeZone(ZoneId.of("America/Sao_Paulo")));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public DirectExchange clubExchange() {
        return new DirectExchange(RabbitMQ.EXCHANGE_CLUB);
    }

    @Bean
    public DirectExchange clubDeadExchange() {
        return new DirectExchange(RabbitMQ.EXCHANGE_DEAD_CLUB);
    }

    //ORDER
    @Bean
    public Queue orderCreateQueue() {
        return QueueBuilder.durable(RabbitMQ.CREATE_ORDER_QUEUE)
                .withArgument(XDLE, RabbitMQ.EXCHANGE_DEAD_CLUB)
                .withArgument(XDLRK, RabbitMQ.CREATE_ORDER_DEAD_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue orderCreateQueueDead() {
        return QueueBuilder.durable(RabbitMQ.CREATE_ORDER_QUEUE_DEAD).build();
    }

    @Bean
    public Binding orderCreateBinding() {
        return new Binding(RabbitMQ.CREATE_ORDER_QUEUE, Binding.DestinationType.QUEUE, RabbitMQ.EXCHANGE_CLUB,
                RabbitMQ.CREATE_ORDER_ROUTING_KEY, null);
    }

    @Bean
    public Binding orderCreateDeadBinding() {
        return new Binding(RabbitMQ.CREATE_ORDER_QUEUE_DEAD, Binding.DestinationType.QUEUE, RabbitMQ.EXCHANGE_DEAD_CLUB,
                RabbitMQ.CREATE_ORDER_DEAD_ROUTING_KEY, null);
    }

    //CUSTOMER
    @Bean
    public Queue customerCreateQueue() {
        return QueueBuilder.durable(RabbitMQ.CREATE_CUSTOMER_QUEUE)
                .withArgument(XDLE, RabbitMQ.EXCHANGE_DEAD_CLUB)
                .withArgument(XDLRK, RabbitMQ.CREATE_CUSTOMER_DEAD_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue customerCreateQueueDead() {
        return QueueBuilder.durable(RabbitMQ.CREATE_CUSTOMER_QUEUE_DEAD).build();
    }

    @Bean
    public Binding customerCreateBinding() {
        return new Binding(RabbitMQ.CREATE_CUSTOMER_QUEUE, Binding.DestinationType.QUEUE, RabbitMQ.EXCHANGE_CLUB,
                RabbitMQ.CREATE_CUSTOMER_ROUTING_KEY, null);
    }

    @Bean
    public Binding customerCreateDeadBinding() {
        return new Binding(RabbitMQ.CREATE_CUSTOMER_QUEUE_DEAD, Binding.DestinationType.QUEUE, RabbitMQ.EXCHANGE_DEAD_CLUB,
                RabbitMQ.CREATE_CUSTOMER_DEAD_ROUTING_KEY, null);
    }

    //CUSTOMER DISPATCHED
    @Bean
    public Queue customerDispatchedQueue() {
        return QueueBuilder.durable(RabbitMQ.DISPATCHED_CUSTOMER_QUEUE)
                .withArgument(XDLE, RabbitMQ.EXCHANGE_DEAD_CLUB)
                .withArgument(XDLRK, RabbitMQ.DISPATCHED_CUSTOMER_DEAD_ROUTING_KEY)
                //.ttl(10000)
                .build();
    }

    @Bean
    public Queue customerDispatchedQueueDead() {
        return QueueBuilder.durable(RabbitMQ.DISPATCHED_CUSTOMER_QUEUE_DEAD).build();
    }

    @Bean
    public Binding customerDispatchedBinding() {
        return new Binding(RabbitMQ.DISPATCHED_CUSTOMER_QUEUE, Binding.DestinationType.QUEUE, RabbitMQ.EXCHANGE_CLUB,
                RabbitMQ.DISPATCHED_CUSTOMER_ROUTING_KEY, null);
    }

    @Bean
    public Binding customerDispatchedDeadBinding() {
        return new Binding(RabbitMQ.DISPATCHED_CUSTOMER_QUEUE_DEAD, Binding.DestinationType.QUEUE, RabbitMQ.EXCHANGE_DEAD_CLUB,
                RabbitMQ.DISPATCHED_CUSTOMER_DEAD_ROUTING_KEY, null);
    }


    @Bean
    public Declarables bindings() {
        return new Declarables(
                orderCreateBinding(),
                orderCreateDeadBinding(),
                customerCreateBinding(),
                customerCreateDeadBinding(),
                customerDispatchedBinding(),
                customerDispatchedDeadBinding()
        );
    }


}