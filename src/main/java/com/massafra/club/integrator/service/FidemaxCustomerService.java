package com.massafra.club.integrator.service;

import com.massafra.club.integrator.constant.FidemaxCustomerInternalParams;
import com.massafra.club.integrator.constant.RabbitMq;
import com.massafra.club.integrator.publisher.Publisher;
import com.massafra.club.integrator.record.FidemaxCustomerRecord;
import com.massafra.club.integrator.repository.FidemaxCustomerRepository;
import com.massafra.club.integrator.specification.SpecificationFidemaxCustomer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class FidemaxCustomerService {

    private final FidemaxCustomerRepository repository;

    private final ModelMapper mapper;

    private final Publisher publisher;

    @Transactional(rollbackOn = Exception.class)
    public void dispatchCustomer() {
        log.info("FidemaxCustomerService.dispatchCustomer - Start");

        var sortBy = Sort.by(Direction.valueOf(FidemaxCustomerInternalParams.PAGINATE_SORT_DIRECTION_DEFAULT),
                FidemaxCustomerInternalParams.PAGINATE_SORT_PROPERTIES_DEFAULT);

        var page = PageRequest.of(FidemaxCustomerInternalParams.PAGINATE_PAGE_DEFAULT, FidemaxCustomerInternalParams.PAGINATE_ROWS_DEFAULT, sortBy);

        var customers = repository.findAll(SpecificationFidemaxCustomer.findByCriteria(),
                page);

        var date = ZonedDateTime.now(ZoneId.of("UTC-3"));
        var integrationTime = date.getHour() + ":" + date.getMinute();
        var integrationDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));


        customers.forEach(customer -> {
            try {
                repository.updateIntegration(customer.getId(), integrationTime, integrationDate);
                publisher.sendAsMessage(RabbitMq.EXCHANGE_CLUB, RabbitMq.CREATE_CUSTOMER_ROUTING_KEY, mapper.map(customer, FidemaxCustomerRecord.class));
            } catch (Exception e) {
                log.error("FidemaxCustomerService.dispatchCustomer - Error - message: {}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });

        log.info("FidemaxCustomerService.dispatchCustomer - End");

    }
}
