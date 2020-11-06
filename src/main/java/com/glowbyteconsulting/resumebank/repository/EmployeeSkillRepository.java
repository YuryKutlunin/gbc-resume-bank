package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.EmployeeSkill;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmployeeSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {
}
