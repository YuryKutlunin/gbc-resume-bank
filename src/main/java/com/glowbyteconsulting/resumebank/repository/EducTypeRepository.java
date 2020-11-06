package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.EducType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EducType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducTypeRepository extends JpaRepository<EducType, Long> {
}
