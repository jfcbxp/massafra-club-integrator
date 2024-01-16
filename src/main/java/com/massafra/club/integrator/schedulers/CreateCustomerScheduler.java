package com.massafra.club.integrator.schedulers;

import com.massafra.club.integrator.services.FidemaxCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateCustomerScheduler {

    private final FidemaxCustomerService service;


    @Value("${create-customer.scheduler.least-lock-time}")
    private String leastLockTimeString;

    @Scheduled(
            cron = "${create-customer.scheduler.cron-value}",
            zone = "America/Sao_Paulo"
    )
    @SchedulerLock(
            name = "REPROCESS_EVENTS",
            lockAtLeastFor = "${create-customer.scheduler.least-lock-time}",
            lockAtMostFor = "${create-customer.scheduler.most-lock-time}"
    )
    public void checkIncomingCustomers() {
        var start = System.currentTimeMillis();
        try {
            log.info("CreateCustomerScheduler.checkIncomingCustomers - Start");
            service.dispatchCustomer();

        } finally {
            log.info("CreateCustomerScheduler.checkIncomingCustomers - End - took [{}ms]", (System.currentTimeMillis() - start));
        }
    }
}