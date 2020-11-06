package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.Skill;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
