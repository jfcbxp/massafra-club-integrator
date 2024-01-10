package com.massafra.club.integrator.converter;


import com.massafra.club.integrator.domain.FidemaxCustomer;
import com.massafra.club.integrator.dto.FidemaxCustomerDTO;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class FidemaxCustomerConverter extends AbstractConverter<FidemaxCustomer, FidemaxCustomerDTO> {
    @Override
    protected FidemaxCustomerDTO convert(FidemaxCustomer fidemaxCustomer) {
        return new FidemaxCustomerDTO(fidemaxCustomer.getNome(),
                fidemaxCustomer.getCgc(),
                fidemaxCustomer.getDataNascimento(),
                fidemaxCustomer.getDdd() + fidemaxCustomer.getTelefone(),
                fidemaxCustomer.getCgc(),
                fidemaxCustomer.getEndereco());
    }
}