package com.massafra.club.integrator.converters;


import com.massafra.club.integrator.domains.FidemaxOrderRefundProfessional;
import com.massafra.club.integrator.enums.PointsTypeEnum;
import com.massafra.club.integrator.records.FidemaxLoyaltynternalRecord;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@Component
public class FidemaxOrderRefundProfessionalConverter extends AbstractConverter<FidemaxOrderRefundProfessional, FidemaxLoyaltynternalRecord> {
    @Override
    protected FidemaxLoyaltynternalRecord convert(FidemaxOrderRefundProfessional order) {
        return new FidemaxLoyaltynternalRecord(
                order.getCgc(),
                order.getDdd().concat(order.getTelefone()),
                this.getPoints(order),
                order.getDocumento(),
                order.getEmpresa().concat(order.getDocumento()).concat(order.getSerie()),
                true);

    }

    private BigInteger getPoints(FidemaxOrderRefundProfessional order) {
        return order.getProdutos().stream().map(item -> {
            Map<PointsTypeEnum, BigDecimal> multipliers = new EnumMap<>(PointsTypeEnum.class);
            multipliers.put(PointsTypeEnum.PROFESSIONAL, order.getPercentualTipoProfissional());
            multipliers.put(PointsTypeEnum.BRAND, item.getPercentualMarca());
            multipliers.put(PointsTypeEnum.GROUP, item.getPercentualGrupo());

            var multiplier = Collections.max(multipliers.values());

            if (multiplier.signum() != 1)
                return BigInteger.ZERO;
            
            return item.getTotal()
                    .multiply(multiplier)
                    .toBigInteger();

        }).reduce(BigInteger.ZERO, BigInteger::add);

    }
}