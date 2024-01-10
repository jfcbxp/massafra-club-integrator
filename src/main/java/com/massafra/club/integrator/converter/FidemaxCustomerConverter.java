package com.massafra.club.integrator.converter;


import com.massafra.club.integrator.domain.FidemaxCustomer;
import com.massafra.club.integrator.record.FidemaxCustomerRecord;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class FidemaxCustomerConverter extends AbstractConverter<FidemaxCustomer, FidemaxCustomerRecord> {
    @Override
    protected FidemaxCustomerRecord convert(FidemaxCustomer fidemaxCustomer) {
        return new FidemaxCustomerRecord(fidemaxCustomer.getNome(),
                fidemaxCustomer.getCgc(),
                fidemaxCustomer.getDataNascimento(),
                fidemaxCustomer.getDdd() + fidemaxCustomer.getTelefone(),
                fidemaxCustomer.getCgc(),
                fidemaxCustomer.getEndereco());
    }
}