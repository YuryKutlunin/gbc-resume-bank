package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.ProjectTechnology;
import com.glowbyteconsulting.resumebank.repository.ProjectTechnologyRepository;
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
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.ProjectTechnology}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjectTechnologyResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTechnologyResource.class);

    private static final String ENTITY_NAME = "projectTechnology";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTechnologyRepository projectTechnologyRepository;

    public ProjectTechnologyResource(ProjectTechnologyRepository projectTechnologyRepository) {
        this.projectTechnologyRepository = projectTechnologyRepository;
    }

    /**
     * {@code POST  /project-technologies} : Create a new projectTechnology.
     *
     * @param projectTechnology the projectTechnology to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTechnology, or with status {@code 400 (Bad Request)} if the projectTechnology has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-technologies")
    public ResponseEntity<ProjectTechnology> createProjectTechnology(@RequestBody ProjectTechnology projectTechnology) throws URISyntaxException {
        log.debug("REST request to save ProjectTechnology : {}", projectTechnology);
        if (projectTechnology.getId() != null) {
            throw new BadRequestAlertException("A new projectTechnology cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTechnology result = projectTechnologyRepository.save(projectTechnology);
        return ResponseEntity.created(new URI("/api/project-technologies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-technologies} : Updates an existing projectTechnology.
     *
     * @param projectTechnology the projectTechnology to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTechnology,
     * or with status {@code 400 (Bad Request)} if the projectTechnology is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTechnology couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-technologies")
    public ResponseEntity<ProjectTechnology> updateProjectTechnology(@RequestBody ProjectTechnology projectTechnology) throws URISyntaxException {
        log.debug("REST request to update ProjectTechnology : {}", projectTechnology);
        if (projectTechnology.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTechnology result = projectTechnologyRepository.save(projectTechnology);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTechnology.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-technologies} : get all the projectTechnologies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTechnologies in body.
     */
    @GetMapping("/project-technologies")
    public List<ProjectTechnology> getAllProjectTechnologies() {
        log.debug("REST request to get all ProjectTechnologies");
        return projectTechnologyRepository.findAll();
    }

    /**
     * {@code GET  /project-technologies/:id} : get the "id" projectTechnology.
     *
     * @param id the id of the projectTechnology to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTechnology, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-technologies/{id}")
    public ResponseEntity<ProjectTechnology> getProjectTechnology(@PathVariable Long id) {
        log.debug("REST request to get ProjectTechnology : {}", id);
        Optional<ProjectTechnology> projectTechnology = projectTechnologyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectTechnology);
    }

    /**
     * {@code DELETE  /project-technologies/:id} : delete the "id" projectTechnology.
     *
     * @param id the id of the projectTechnology to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-technologies/{id}")
    public ResponseEntity<Void> deleteProjectTechnology(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTechnology : {}", id);
        projectTechnologyRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
