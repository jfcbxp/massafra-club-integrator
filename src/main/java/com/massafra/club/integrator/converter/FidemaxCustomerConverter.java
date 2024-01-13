package com.massafra.club.integrator.converter;


import com.massafra.club.integrator.domain.FidemaxCustomer;
import com.massafra.club.integrator.record.FidemaxCustomerInternalRecord;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class FidemaxCustomerConverter extends AbstractConverter<FidemaxCustomer, FidemaxCustomerInternalRecord> {
    @Override
    protected FidemaxCustomerInternalRecord convert(FidemaxCustomer fidemaxCustomer) {
        return new FidemaxCustomerInternalRecord(fidemaxCustomer.getNome(),
                fidemaxCustomer.getCgc(),
                fidemaxCustomer.getDataNascimento(),
                fidemaxCustomer.getDdd() + fidemaxCustomer.getTelefone(),
                fidemaxCustomer.getCgc(),
                fidemaxCustomer.getEndereco(),
                fidemaxCustomer.getCep(),
                fidemaxCustomer.getBairro(),
                fidemaxCustomer.getComplemento());
    }
}