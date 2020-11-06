package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.JobTitle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobTitle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
}
