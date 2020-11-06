package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.ProjectRole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Long> {
}
