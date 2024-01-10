package com.massafra.club.integrator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.massafra.club.integrator.constant.FidemaxCustomerInternalParams;
import com.massafra.club.integrator.constant.RabbitMq;
import com.massafra.club.integrator.domain.FidemaxCustomer;
import com.massafra.club.integrator.dto.FidemaxCustomerDTO;
import com.massafra.club.integrator.publisher.Publisher;
import com.massafra.club.integrator.repository.FidemaxCustomerRepository;
import com.massafra.club.integrator.specification.SpecificationQueryFidemaxCustomerByFilter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class FidemaxCustomerService {

    private final FidemaxCustomerRepository repository;

    private final ModelMapper mapper;

    private final Publisher publisher;

    @Transactional(rollbackOn = Exception.class)
    public void dispatchCustomer() {
        Sort sortBy = Sort.by(Direction.valueOf(FidemaxCustomerInternalParams.PAGINATE_SORT_DIRECTION_DEFAULT),
                FidemaxCustomerInternalParams.PAGINATE_SORT_PROPERTIES_DEFAULT);

        PageRequest page = PageRequest.of(FidemaxCustomerInternalParams.PAGINATE_PAGE_DEFAULT, FidemaxCustomerInternalParams.PAGINATE_ROWS_DEFAULT, sortBy);

        Page<FidemaxCustomer> customers = repository.findAll(SpecificationQueryFidemaxCustomerByFilter.findByCriteria(),
                page);

        customers.forEach(customer -> {
            try {
                customer.setDataIntegracao(LocalDate.now());
                customer.setHoraIntegracao(LocalDateTime.now().getHour() + ":" + LocalDateTime.MAX.getMinute());
                //repository.save(customer);
                publisher.sendAsMessage(RabbitMq.EXCHANGE_CLUB, RabbitMq.CREATE_CUSTOMER_ROUTING_KEY, mapper.map(customer, FidemaxCustomerDTO.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
