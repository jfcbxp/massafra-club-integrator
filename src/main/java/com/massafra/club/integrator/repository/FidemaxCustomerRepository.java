package com.massafra.club.integrator.repository;

import com.massafra.club.integrator.domain.FidemaxCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FidemaxCustomerRepository extends JpaRepository<FidemaxCustomer, Integer> {
    Page<FidemaxCustomer> findAll(Specification<FidemaxCustomer> criteria, Pageable sort);

}

