package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.University;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the University entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
}
