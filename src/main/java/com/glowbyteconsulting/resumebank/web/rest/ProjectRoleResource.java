package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.ProjectRole;
import com.glowbyteconsulting.resumebank.repository.ProjectRoleRepository;
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
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.ProjectRole}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjectRoleResource {

    private final Logger log = LoggerFactory.getLogger(ProjectRoleResource.class);

    private static final String ENTITY_NAME = "projectRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectRoleRepository projectRoleRepository;

    public ProjectRoleResource(ProjectRoleRepository projectRoleRepository) {
        this.projectRoleRepository = projectRoleRepository;
    }

    /**
     * {@code POST  /project-roles} : Create a new projectRole.
     *
     * @param projectRole the projectRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectRole, or with status {@code 400 (Bad Request)} if the projectRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-roles")
    public ResponseEntity<ProjectRole> createProjectRole(@RequestBody ProjectRole projectRole) throws URISyntaxException {
        log.debug("REST request to save ProjectRole : {}", projectRole);
        if (projectRole.getId() != null) {
            throw new BadRequestAlertException("A new projectRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectRole result = projectRoleRepository.save(projectRole);
        return ResponseEntity.created(new URI("/api/project-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-roles} : Updates an existing projectRole.
     *
     * @param projectRole the projectRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectRole,
     * or with status {@code 400 (Bad Request)} if the projectRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-roles")
    public ResponseEntity<ProjectRole> updateProjectRole(@RequestBody ProjectRole projectRole) throws URISyntaxException {
        log.debug("REST request to update ProjectRole : {}", projectRole);
        if (projectRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectRole result = projectRoleRepository.save(projectRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-roles} : get all the projectRoles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectRoles in body.
     */
    @GetMapping("/project-roles")
    public List<ProjectRole> getAllProjectRoles() {
        log.debug("REST request to get all ProjectRoles");
        return projectRoleRepository.findAll();
    }

    /**
     * {@code GET  /project-roles/:id} : get the "id" projectRole.
     *
     * @param id the id of the projectRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-roles/{id}")
    public ResponseEntity<ProjectRole> getProjectRole(@PathVariable Long id) {
        log.debug("REST request to get ProjectRole : {}", id);
        Optional<ProjectRole> projectRole = projectRoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectRole);
    }

    /**
     * {@code DELETE  /project-roles/:id} : delete the "id" projectRole.
     *
     * @param id the id of the projectRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-roles/{id}")
    public ResponseEntity<Void> deleteProjectRole(@PathVariable Long id) {
        log.debug("REST request to delete ProjectRole : {}", id);
        projectRoleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
