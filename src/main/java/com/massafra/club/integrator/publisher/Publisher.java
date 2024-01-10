package com.massafra.club.integrator.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class Publisher {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public <T> void sendAsMessage(final String exchange, final String routingKey, final T body) throws JsonProcessingException {
        log.debug("Publisher.sendAsMessage - Start - exchange [{}], routingKey [{}], body [{}]", exchange, routingKey, body);

        var payloadAsString = mapper.writeValueAsString(body);
        var message = MessageBuilder.withBody(payloadAsString.getBytes(StandardCharsets.UTF_8)).build();

        rabbitTemplate.convertAndSend(exchange, routingKey, message);

        log.info("Publisher.sendAsMessage - Processed - exchange [{}] routingKey [{}]", exchange, routingKey);
    }

    public <T> void sendAsString(final String exchange, final String routingKey, final T body) throws JsonProcessingException {
        log.debug("Publisher.sendAsString - Start - exchange [{}] routingKey [{}] body  [{}]", exchange, routingKey, body);

        var payloadAsString = mapper.writeValueAsString(body);

        rabbitTemplate.convertAndSend(exchange, routingKey, payloadAsString);
        
        log.info("Publisher.sendAsString - Processed - exchange [{}] routingKey [{}]", exchange, routingKey);
    }

}