package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.Certificate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Certificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
