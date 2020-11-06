package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.JobTitle;
import com.glowbyteconsulting.resumebank.repository.JobTitleRepository;
import com.glowbyteconsulting.resumebank.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.JobTitle}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JobTitleResource {

    private final Logger log = LoggerFactory.getLogger(JobTitleResource.class);

    private static final String ENTITY_NAME = "jobTitle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobTitleRepository jobTitleRepository;

    public JobTitleResource(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    /**
     * {@code POST  /job-titles} : Create a new jobTitle.
     *
     * @param jobTitle the jobTitle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobTitle, or with status {@code 400 (Bad Request)} if the jobTitle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-titles")
    public ResponseEntity<JobTitle> createJobTitle(@RequestBody JobTitle jobTitle) throws URISyntaxException {
        log.debug("REST request to save JobTitle : {}", jobTitle);
        if (jobTitle.getId() != null) {
            throw new BadRequestAlertException("A new jobTitle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobTitle result = jobTitleRepository.save(jobTitle);
        return ResponseEntity.created(new URI("/api/job-titles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-titles} : Updates an existing jobTitle.
     *
     * @param jobTitle the jobTitle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobTitle,
     * or with status {@code 400 (Bad Request)} if the jobTitle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobTitle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-titles")
    public ResponseEntity<JobTitle> updateJobTitle(@RequestBody JobTitle jobTitle) throws URISyntaxException {
        log.debug("REST request to update JobTitle : {}", jobTitle);
        if (jobTitle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobTitle result = jobTitleRepository.save(jobTitle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobTitle.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-titles} : get all the jobTitles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobTitles in body.
     */
    @GetMapping("/job-titles")
    public List<JobTitle> getAllJobTitles() {
        log.debug("REST request to get all JobTitles");
        return jobTitleRepository.findAll();
    }

    /**
     * {@code GET  /job-titles/:id} : get the "id" jobTitle.
     *
     * @param id the id of the jobTitle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobTitle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-titles/{id}")
    public ResponseEntity<JobTitle> getJobTitle(@PathVariable Long id) {
        log.debug("REST request to get JobTitle : {}", id);
        Optional<JobTitle> jobTitle = jobTitleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobTitle);
    }

    /**
     * {@code DELETE  /job-titles/:id} : delete the "id" jobTitle.
     *
     * @param id the id of the jobTitle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-titles/{id}")
    public ResponseEntity<Void> deleteJobTitle(@PathVariable Long id) {
        log.debug("REST request to delete JobTitle : {}", id);
        jobTitleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
