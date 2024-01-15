package com.massafra.club.integrator.converters;


import com.massafra.club.integrator.domains.FidemaxCustomer;
import com.massafra.club.integrator.records.FidemaxCustomerInternalRecord;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class FidemaxCustomerConverter extends AbstractConverter<FidemaxCustomer, FidemaxCustomerInternalRecord> {
    @Override
    protected FidemaxCustomerInternalRecord convert(FidemaxCustomer fidemaxCustomer) {
        return new FidemaxCustomerInternalRecord(fidemaxCustomer.getNome(),
                fidemaxCustomer.getCgc(),
                fidemaxCustomer.getEmail(),
                fidemaxCustomer.getDataNascimento(),
                fidemaxCustomer.getDdd() + fidemaxCustomer.getTelefone(),
                fidemaxCustomer.getCgc(),
                fidemaxCustomer.getEndereco(),
                fidemaxCustomer.getCep(),
                fidemaxCustomer.getBairro(),
                fidemaxCustomer.getComplemento(),
                fidemaxCustomer.getId());
    }
}