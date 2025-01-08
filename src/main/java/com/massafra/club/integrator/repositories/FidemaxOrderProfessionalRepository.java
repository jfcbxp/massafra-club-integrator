package com.massafra.club.integrator.repositories;

import com.massafra.club.integrator.domains.FidemaxOrderProfessional;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FidemaxOrderProfessionalRepository extends JpaRepository<FidemaxOrderProfessional, Integer> {
    @NotNull
    Page<FidemaxOrderProfessional> findAll(Pageable sort);

    @Transactional
    @Modifying(
            clearAutomatically = true,
            flushAutomatically = true
    )
    @Query(
            value = "UPDATE SF2010 SET F2_ZDTFIDP = :dataIntegracao where R_E_C_N_O_ = :id",
            nativeQuery = true
    )
    void updateIntegration(
            @Param("id")
            Integer id,
            @Param("dataIntegracao")
            String dataIntegracao
    );

}

