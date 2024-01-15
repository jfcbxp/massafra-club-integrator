package com.massafra.club.integrator.listeners;


import com.massafra.club.integrator.annotations.Listener;
import com.massafra.club.integrator.constants.RabbitMQ;
import com.massafra.club.integrator.services.FidemaxCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@Listener
@RequiredArgsConstructor
public class FidemaxCustomerDispatchedListener {

    private final FidemaxCustomerService service;

    @RabbitListener(queues = RabbitMQ.DISPATCHED_CUSTOMER_QUEUE)
    public void processDispatchedCustomerListener(
            @Header(AmqpHeaders.RECEIVED_ROUTING_KEY)
            String routingKey,
            @Payload
            String payload) {

        log.info("FidemaxCustomerDispatchedListener.processDispatchedCustomerListener - Start - routingKey: [{}], id: [{}]", routingKey, payload);

        service.dispatchedCustomer(Integer.parseInt(payload));

        log.info("FidemaxCustomerDispatchedListener.processDispatchedCustomerListener - End - routingKey: [{}]", routingKey);
    }
}
