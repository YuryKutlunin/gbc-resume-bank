package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.EmployeeProject;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeProject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
}
