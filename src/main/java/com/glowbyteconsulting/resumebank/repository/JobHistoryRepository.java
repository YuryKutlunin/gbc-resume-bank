package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.JobHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, Long> {
}
