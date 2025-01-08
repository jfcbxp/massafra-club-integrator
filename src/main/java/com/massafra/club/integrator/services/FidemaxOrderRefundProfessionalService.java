package com.massafra.club.integrator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.massafra.club.integrator.constants.FidemaxCustomerInternalParams;
import com.massafra.club.integrator.constants.RabbitMQ;
import com.massafra.club.integrator.domains.FidemaxOrderRefundProfessional;
import com.massafra.club.integrator.publishers.Publisher;
import com.massafra.club.integrator.records.FidemaxLoyaltynternalRecord;
import com.massafra.club.integrator.repositories.FidemaxOrderRefundProfessionalRepository;
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
public class FidemaxOrderRefundProfessionalService {

    private final FidemaxOrderRefundProfessionalRepository repository;

    private final ModelMapper mapper;

    private final Publisher publisher;

    @Transactional(rollbackOn = Exception.class)
    public void dispatchSaleOrderRefund() {
        log.info("FidemaxOrderRefundProfessionalService.dispatchSaleOrderRefund - Start");

        var sortBy = Sort.by(Direction.valueOf(FidemaxCustomerInternalParams.PAGINATE_SORT_DIRECTION_DEFAULT),
                FidemaxCustomerInternalParams.PAGINATE_SORT_PROPERTIES_DEFAULT);

        var page = PageRequest.of(FidemaxCustomerInternalParams.PAGINATE_PAGE_DEFAULT, FidemaxCustomerInternalParams.PAGINATE_ROWS_DEFAULT, sortBy);

        repository.findAll(page).forEach(this::dispatchLoyaltyRefund);

        log.info("FidemaxOrderRefundProfessionalService.dispatchSaleOrderRefund - End");

    }


    private void dispatchLoyaltyRefund(FidemaxOrderRefundProfessional order) {
        log.info("FidemaxOrderRefundProfessionalService.dispatchLoyalty - Start");

        dispatchedOrderRefund(order.getId());

        var loyalty = mapper.map(order, FidemaxLoyaltynternalRecord.class);

        try {
            publisher.sendAsMessage(RabbitMQ.EXCHANGE_CLUB, RabbitMQ.CREATE_ORDER_ROUTING_KEY, loyalty);
        } catch (JsonProcessingException e) {
            log.error("FidemaxCustomerService.dispatchLoyalty - Error - message: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        log.info("FidemaxOrderRefundProfessionalService.dispatchLoyalty - End");

    }

    private void dispatchedOrderRefund(Integer id) {
        log.info("FidemaxOrderRefundProfessionalService.dispatchedOrderRefund - Start");

        var date = ZonedDateTime.now(ZoneId.of("UTC-3"));
        var integrationDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        repository.updateIntegration(id, integrationDate);

        log.info("FidemaxOrderRefundProfessionalService.dispatchedOrderRefund - End");

    }


}
