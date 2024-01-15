package com.massafra.club.integrator.repositorys;

import com.massafra.club.integrator.domains.FidemaxCustomer;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FidemaxCustomerRepository extends JpaRepository<FidemaxCustomer, Integer> {
    Page<FidemaxCustomer> findAll(Specification<FidemaxCustomer> criteria, Pageable sort);

    @Transactional
    @Modifying(
            clearAutomatically = true,
            flushAutomatically = true
    )
    @Query(
            value = "UPDATE ZFB010 SET ZFB_HINTEG = :horaIntegracao, ZFB_DTINTE = :dataIntegracao where R_E_C_N_O_ = :id",
            nativeQuery = true
    )
    void updateIntegration(
            @Param("id")
            Integer id,
            @Param("horaIntegracao")
            String horaIntegracao,
            @Param("dataIntegracao")
            String dataIntegracao
    );

}

