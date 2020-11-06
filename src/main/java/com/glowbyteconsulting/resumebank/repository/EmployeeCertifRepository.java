package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.EmployeeCertif;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeCertif entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeCertifRepository extends JpaRepository<EmployeeCertif, Long> {
}
