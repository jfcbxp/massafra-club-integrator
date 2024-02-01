package com.massafra.club.integrator.converters;


import com.massafra.club.integrator.domains.FidemaxOrderProfessional;
import com.massafra.club.integrator.records.FidemaxLoyaltynternalRecord;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class FidemaxOrderProfessionalConverter extends AbstractConverter<FidemaxOrderProfessional, FidemaxLoyaltynternalRecord> {
    @Override
    protected FidemaxLoyaltynternalRecord convert(FidemaxOrderProfessional order) {
        return new FidemaxLoyaltynternalRecord(
                order.getCgc(),
                order.getDdd().concat(order.getTelefone()),
                BigInteger.ZERO,
                order.getDocumento(),
                order.getEmpresa().concat(order.getDocumento()).concat(order.getSerie()),
                false);
    }
}