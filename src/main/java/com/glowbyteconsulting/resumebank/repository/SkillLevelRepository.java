package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.SkillLevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SkillLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillLevelRepository extends JpaRepository<SkillLevel, Long> {
}
