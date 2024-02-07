package com.massafra.club.integrator.schedulers;

import com.massafra.club.integrator.services.FidemaxOrderProfessionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateOrderScheduler {

    private final FidemaxOrderProfessionalService service;


    @Value("${create-order.scheduler.least-lock-time}")
    private String leastLockTimeString;

    @Scheduled(
            cron = "${create-order.scheduler.cron-value}",
            zone = "America/Sao_Paulo"
    )
    @SchedulerLock(
            name = "CREATE_ORDERS",
            lockAtLeastFor = "${create-order.scheduler.least-lock-time}",
            lockAtMostFor = "${create-order.scheduler.most-lock-time}"
    )
    public void checkIncomingOrders() {
        var start = System.currentTimeMillis();
        try {
            log.info("FidemaxOrderProfessionalService.checkIncomingOrders - Start");
            service.dispatchSaleOrder();

        } finally {
            log.info("FidemaxOrderProfessionalService.checkIncomingOrders - End - took [{}ms]", (System.currentTimeMillis() - start));
        }
    }
}