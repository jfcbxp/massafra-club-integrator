package com.massafra.club.integrator.services;

import com.massafra.club.integrator.constants.FidemaxCustomerInternalParams;
import com.massafra.club.integrator.constants.RabbitMQ;
import com.massafra.club.integrator.publishers.Publisher;
import com.massafra.club.integrator.records.FidemaxCustomerInternalRecord;
import com.massafra.club.integrator.repositories.FidemaxCustomerRepository;
import com.massafra.club.integrator.specifications.SpecificationFidemaxCustomer;
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

    public void dispatchCustomer() {
        log.info("FidemaxCustomerService.dispatchCustomer - Start");

        var sortBy = Sort.by(Direction.valueOf(FidemaxCustomerInternalParams.PAGINATE_SORT_DIRECTION_DEFAULT),
                FidemaxCustomerInternalParams.PAGINATE_SORT_PROPERTIES_DEFAULT);

        var page = PageRequest.of(FidemaxCustomerInternalParams.PAGINATE_PAGE_DEFAULT, FidemaxCustomerInternalParams.PAGINATE_ROWS_DEFAULT, sortBy);

        var customers = repository.findAll(SpecificationFidemaxCustomer.findByCriteria(),
                page);


        customers.forEach(customer -> {
            try {
                publisher.sendAsMessage(RabbitMQ.EXCHANGE_CLUB, RabbitMQ.CREATE_CUSTOMER_ROUTING_KEY, mapper.map(customer, FidemaxCustomerInternalRecord.class));
            } catch (Exception e) {
                log.error("FidemaxCustomerService.dispatchCustomer - Error - message: {}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });

        log.info("FidemaxCustomerService.dispatchCustomer - End");

    }

    @Transactional(rollbackOn = Exception.class)
    public void dispatchedCustomer(Integer id) {
        log.info("FidemaxCustomerService.dispatchedCustomer - Start");

        var date = ZonedDateTime.now(ZoneId.of("UTC-3"));
        var integrationTime = date.getHour() + ":" + date.getMinute();
        var integrationDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        repository.updateIntegration(id, integrationTime, integrationDate);

        log.info("FidemaxCustomerService.dispatchedCustomer - End");

    }


}
