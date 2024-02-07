package com.massafra.club.integrator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.massafra.club.integrator.constants.FidemaxCustomerInternalParams;
import com.massafra.club.integrator.constants.RabbitMQ;
import com.massafra.club.integrator.domains.FidemaxOrderProfessional;
import com.massafra.club.integrator.publishers.Publisher;
import com.massafra.club.integrator.records.FidemaxLoyaltynternalRecord;
import com.massafra.club.integrator.repositorys.FidemaxOrderProfessionalRepository;
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
public class FidemaxOrderProfessionalService {

    private final FidemaxOrderProfessionalRepository repository;

    private final ModelMapper mapper;

    private final Publisher publisher;

    @Transactional(rollbackOn = Exception.class)
    public void dispatchSaleOrder() {
        log.info("FidemaxOrderProfessionalService.dispatchSaleOrder - Start");

        var sortBy = Sort.by(Direction.valueOf(FidemaxCustomerInternalParams.PAGINATE_SORT_DIRECTION_DEFAULT),
                FidemaxCustomerInternalParams.PAGINATE_SORT_PROPERTIES_DEFAULT);

        var page = PageRequest.of(FidemaxCustomerInternalParams.PAGINATE_PAGE_DEFAULT, FidemaxCustomerInternalParams.PAGINATE_ROWS_DEFAULT, sortBy);

        repository.findAll(page).forEach(this::dispatchLoyalty);

        log.info("FidemaxOrderProfessionalService.dispatchSaleOrder - End");

    }


    private void dispatchLoyalty(FidemaxOrderProfessional order) {
        log.info("FidemaxOrderProfessionalService.dispatchLoyalty - Start");

        dispatchedOrder(order.getId());

        var loyaltys = order.getProdutos().stream()
                .map(product -> mapper.map(product, FidemaxLoyaltynternalRecord.class))
                .filter(loyalty -> loyalty.points().signum() == 1).toList();

        loyaltys.forEach(loyalty -> {
            try {
                publisher.sendAsMessage(RabbitMQ.EXCHANGE_CLUB, RabbitMQ.CREATE_ORDER_ROUTING_KEY, loyalty);
            } catch (JsonProcessingException e) {
                log.error("FidemaxCustomerService.dispatchLoyalty - Error - message: {}", e.getMessage(), e);
                throw new RuntimeException(e);
            }


        });

        log.info("FidemaxOrderProfessionalService.dispatchLoyalty - End");

    }

    private void dispatchedOrder(Integer id) {
        log.info("FidemaxOrderProfessionalService.dispatchedOrder - Start");

        var date = ZonedDateTime.now(ZoneId.of("UTC-3"));
        var integrationDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        repository.updateIntegration(id, integrationDate);

        log.info("FidemaxOrderProfessionalService.dispatchedOrder - End");

    }


}
