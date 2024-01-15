package com.massafra.club.integrator.specifications;


import com.massafra.club.integrator.domains.FidemaxCustomer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpecificationFidemaxCustomer {

    private static final String HORA_INTEGRACAO = "horaIntegracao";

    public static Specification<FidemaxCustomer> findByCriteria() {
        return (root, query, cb) -> {

            return cb.equal(root.get(HORA_INTEGRACAO), "");
        };
    }
}
