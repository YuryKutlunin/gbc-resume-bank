package com.glowbyteconsulting.resumebank.repository;

import com.glowbyteconsulting.resumebank.domain.Task;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
