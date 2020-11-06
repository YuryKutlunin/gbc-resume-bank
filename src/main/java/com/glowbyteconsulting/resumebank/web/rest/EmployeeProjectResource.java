package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.EmployeeProject;
import com.glowbyteconsulting.resumebank.repository.EmployeeProjectRepository;
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
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.EmployeeProject}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmployeeProjectResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectResource.class);

    private static final String ENTITY_NAME = "employeeProject";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeProjectRepository employeeProjectRepository;

    public EmployeeProjectResource(EmployeeProjectRepository employeeProjectRepository) {
        this.employeeProjectRepository = employeeProjectRepository;
    }

    /**
     * {@code POST  /employee-projects} : Create a new employeeProject.
     *
     * @param employeeProject the employeeProject to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeProject, or with status {@code 400 (Bad Request)} if the employeeProject has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-projects")
    public ResponseEntity<EmployeeProject> createEmployeeProject(@RequestBody EmployeeProject employeeProject) throws URISyntaxException {
        log.debug("REST request to save EmployeeProject : {}", employeeProject);
        if (employeeProject.getId() != null) {
            throw new BadRequestAlertException("A new employeeProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeProject result = employeeProjectRepository.save(employeeProject);
        return ResponseEntity.created(new URI("/api/employee-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-projects} : Updates an existing employeeProject.
     *
     * @param employeeProject the employeeProject to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeProject,
     * or with status {@code 400 (Bad Request)} if the employeeProject is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeProject couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-projects")
    public ResponseEntity<EmployeeProject> updateEmployeeProject(@RequestBody EmployeeProject employeeProject) throws URISyntaxException {
        log.debug("REST request to update EmployeeProject : {}", employeeProject);
        if (employeeProject.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeProject result = employeeProjectRepository.save(employeeProject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeProject.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-projects} : get all the employeeProjects.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeProjects in body.
     */
    @GetMapping("/employee-projects")
    public List<EmployeeProject> getAllEmployeeProjects() {
        log.debug("REST request to get all EmployeeProjects");
        return employeeProjectRepository.findAll();
    }

    /**
     * {@code GET  /employee-projects/:id} : get the "id" employeeProject.
     *
     * @param id the id of the employeeProject to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeProject, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-projects/{id}")
    public ResponseEntity<EmployeeProject> getEmployeeProject(@PathVariable Long id) {
        log.debug("REST request to get EmployeeProject : {}", id);
        Optional<EmployeeProject> employeeProject = employeeProjectRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employeeProject);
    }

    /**
     * {@code DELETE  /employee-projects/:id} : delete the "id" employeeProject.
     *
     * @param id the id of the employeeProject to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-projects/{id}")
    public ResponseEntity<Void> deleteEmployeeProject(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeProject : {}", id);
        employeeProjectRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
