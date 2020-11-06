package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.University;
import com.glowbyteconsulting.resumebank.repository.UniversityRepository;
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
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.University}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UniversityResource {

    private final Logger log = LoggerFactory.getLogger(UniversityResource.class);

    private static final String ENTITY_NAME = "university";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UniversityRepository universityRepository;

    public UniversityResource(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    /**
     * {@code POST  /universities} : Create a new university.
     *
     * @param university the university to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new university, or with status {@code 400 (Bad Request)} if the university has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/universities")
    public ResponseEntity<University> createUniversity(@RequestBody University university) throws URISyntaxException {
        log.debug("REST request to save University : {}", university);
        if (university.getId() != null) {
            throw new BadRequestAlertException("A new university cannot already have an ID", ENTITY_NAME, "idexists");
        }
        University result = universityRepository.save(university);
        return ResponseEntity.created(new URI("/api/universities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /universities} : Updates an existing university.
     *
     * @param university the university to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated university,
     * or with status {@code 400 (Bad Request)} if the university is not valid,
     * or with status {@code 500 (Internal Server Error)} if the university couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/universities")
    public ResponseEntity<University> updateUniversity(@RequestBody University university) throws URISyntaxException {
        log.debug("REST request to update University : {}", university);
        if (university.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        University result = universityRepository.save(university);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, university.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /universities} : get all the universities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of universities in body.
     */
    @GetMapping("/universities")
    public List<University> getAllUniversities() {
        log.debug("REST request to get all Universities");
        return universityRepository.findAll();
    }

    /**
     * {@code GET  /universities/:id} : get the "id" university.
     *
     * @param id the id of the university to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the university, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/universities/{id}")
    public ResponseEntity<University> getUniversity(@PathVariable Long id) {
        log.debug("REST request to get University : {}", id);
        Optional<University> university = universityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(university);
    }

    /**
     * {@code DELETE  /universities/:id} : delete the "id" university.
     *
     * @param id the id of the university to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/universities/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        log.debug("REST request to delete University : {}", id);
        universityRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
