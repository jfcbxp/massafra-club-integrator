package com.massafra.club.integrator.specification;


import com.massafra.club.integrator.domain.FidemaxCustomer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecificationQueryFidemaxCustomerByFilter {

    private static final String HORA_INTEGRACAO = "horaIntegracao";

    public static Specification<FidemaxCustomer> findByCriteria() {
        return (root, query, cb) -> {

            return cb.notEqual(root.get(HORA_INTEGRACAO), "");
        };
    }
}
