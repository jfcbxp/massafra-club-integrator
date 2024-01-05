package com.massafra.club.integrator.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReprocessEventsScheduler {


    @Value("${reprocessment.scheduler.least-lock-time}")
    private String leastLockTimeString;

    @Scheduled(
            cron = "${reprocessment.scheduler.cron-value}",
            zone = "America/Sao_Paulo"
    )
    @SchedulerLock(
            name = "REPROCESS_EVENTS",
            lockAtLeastFor = "${reprocessment.scheduler.least-lock-time}",
            lockAtMostFor = "${reprocessment.scheduler.most-lock-time}"
    )
    public void checkEventsToReprocess() {
        long start = System.currentTimeMillis();
        try {
            log.debug("ReprocessEventsScheduler.checkEventsToReprocess - Start");

        } finally {
            log.debug("ReprocessEventsScheduler.checkEventsToReprocess - End - took [{}ms]", (System.currentTimeMillis() - start));
        }
    }
}