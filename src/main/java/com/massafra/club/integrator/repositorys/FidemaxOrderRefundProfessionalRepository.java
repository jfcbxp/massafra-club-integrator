package com.massafra.club.integrator.repositorys;

import com.massafra.club.integrator.domains.FidemaxOrderRefundProfessional;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FidemaxOrderRefundProfessionalRepository extends JpaRepository<FidemaxOrderRefundProfessional, Integer> {
    @NotNull
    Page<FidemaxOrderRefundProfessional> findAll(Pageable sort);

    @Transactional
    @Modifying(
            clearAutomatically = true,
            flushAutomatically = true
    )
    @Query(
            value = "UPDATE SF1010 SET F1_ZDTFIDP = :dataIntegracao where R_E_C_N_O_ = :id",
            nativeQuery = true
    )
    void updateIntegration(
            @Param("id")
            Integer id,
            @Param("dataIntegracao")
            String dataIntegracao
    );

}

