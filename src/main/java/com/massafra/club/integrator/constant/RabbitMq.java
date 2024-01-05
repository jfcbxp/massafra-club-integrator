package com.massafra.club.integrator.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class RabbitMq {
    private static final String DEAD_SUFFIX = ".dead";
    public static final String EXCHANGE_CLUB = "club.exchange";
    public static final String EXCHANGE_DEAD_CLUB = EXCHANGE_CLUB + DEAD_SUFFIX ;
    private static final String CLUB_QUEUE_PREFIX = "club.";

    //ORDER
    public static final String CREATE_ORDER_ROUTING_KEY = "create.order";
    public static final String CREATE_ORDER_QUEUE = CLUB_QUEUE_PREFIX + CREATE_ORDER_ROUTING_KEY;
    public static final String CREATE_ORDER_QUEUE_DEAD = CREATE_ORDER_QUEUE + DEAD_SUFFIX;
    public static final String CREATE_ORDER_DEAD_ROUTING_KEY = CREATE_ORDER_ROUTING_KEY + DEAD_SUFFIX;

    //CUSTOMER
    public static final String CREATE_CUSTOMER_ROUTING_KEY = "create.customer";
    public static final String CREATE_CUSTOMER_QUEUE = CLUB_QUEUE_PREFIX + CREATE_CUSTOMER_ROUTING_KEY;
    public static final String CREATE_CUSTOMER_QUEUE_DEAD = CREATE_CUSTOMER_QUEUE + DEAD_SUFFIX;
    public static final String CREATE_CUSTOMER_DEAD_ROUTING_KEY = CREATE_CUSTOMER_ROUTING_KEY + DEAD_SUFFIX;
}