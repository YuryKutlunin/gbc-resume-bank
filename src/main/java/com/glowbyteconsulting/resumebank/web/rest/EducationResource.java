package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.Education;
import com.glowbyteconsulting.resumebank.repository.EducationRepository;
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
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.Education}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EducationResource {

    private final Logger log = LoggerFactory.getLogger(EducationResource.class);

    private static final String ENTITY_NAME = "education";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducationRepository educationRepository;

    public EducationResource(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    /**
     * {@code POST  /educations} : Create a new education.
     *
     * @param education the education to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new education, or with status {@code 400 (Bad Request)} if the education has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educations")
    public ResponseEntity<Education> createEducation(@RequestBody Education education) throws URISyntaxException {
        log.debug("REST request to save Education : {}", education);
        if (education.getId() != null) {
            throw new BadRequestAlertException("A new education cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Education result = educationRepository.save(education);
        return ResponseEntity.created(new URI("/api/educations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /educations} : Updates an existing education.
     *
     * @param education the education to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated education,
     * or with status {@code 400 (Bad Request)} if the education is not valid,
     * or with status {@code 500 (Internal Server Error)} if the education couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educations")
    public ResponseEntity<Education> updateEducation(@RequestBody Education education) throws URISyntaxException {
        log.debug("REST request to update Education : {}", education);
        if (education.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Education result = educationRepository.save(education);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, education.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /educations} : get all the educations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educations in body.
     */
    @GetMapping("/educations")
    public List<Education> getAllEducations() {
        log.debug("REST request to get all Educations");
        return educationRepository.findAll();
    }

    /**
     * {@code GET  /educations/:id} : get the "id" education.
     *
     * @param id the id of the education to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the education, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educations/{id}")
    public ResponseEntity<Education> getEducation(@PathVariable Long id) {
        log.debug("REST request to get Education : {}", id);
        Optional<Education> education = educationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(education);
    }

    /**
     * {@code DELETE  /educations/:id} : delete the "id" education.
     *
     * @param id the id of the education to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educations/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        log.debug("REST request to delete Education : {}", id);
        educationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
