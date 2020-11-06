package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.ProjectTechnology;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectTechnology entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTechnologyRepository extends JpaRepository<ProjectTechnology, Long> {
}
