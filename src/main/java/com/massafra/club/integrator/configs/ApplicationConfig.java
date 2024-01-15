package com.massafra.club.integrator.configs;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableJpaAuditing
@EnableRetry
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "${schedulers.default-most-lock-time}")
public class ApplicationConfig {

}
