package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.Technology;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Technology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}
