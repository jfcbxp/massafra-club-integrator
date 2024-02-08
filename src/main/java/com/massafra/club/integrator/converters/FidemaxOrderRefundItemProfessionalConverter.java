package com.massafra.club.integrator.converters;


import com.massafra.club.integrator.domains.FidemaxOrderRefundItemProfessional;
import com.massafra.club.integrator.enums.PointsTypeEnum;
import com.massafra.club.integrator.records.FidemaxLoyaltynternalRecord;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@Component
public class FidemaxOrderRefundItemProfessionalConverter extends AbstractConverter<FidemaxOrderRefundItemProfessional, FidemaxLoyaltynternalRecord> {
    @Override
    protected FidemaxLoyaltynternalRecord convert(FidemaxOrderRefundItemProfessional orderItem) {
        var multiplier = getMultiplier(orderItem);
        var points = orderItem.getTotal()
                .multiply(multiplier)
                .toBigInteger();

        return new FidemaxLoyaltynternalRecord(
                orderItem.getOrder().getCgc(),
                orderItem.getOrder().getDdd().concat(orderItem.getOrder().getTelefone()),
                points,
                orderItem.getDocumento(),
                orderItem.getEmpresa().concat(orderItem.getDocumento()).concat(orderItem.getSerie()).concat(orderItem.getItem()),
                true);
    }

    private BigDecimal getMultiplier(FidemaxOrderRefundItemProfessional orderItem) {
        Map<PointsTypeEnum, BigDecimal> multipliers = new EnumMap<>(PointsTypeEnum.class);
        multipliers.put(PointsTypeEnum.PROFESSIONAL, orderItem.getOrder().getPercentualTipoProfissional());
        multipliers.put(PointsTypeEnum.BRAND, orderItem.getPercentualMarca());
        multipliers.put(PointsTypeEnum.GROUP, orderItem.getPercentualGrupo());

        return Collections.max(multipliers.values());
    }
}