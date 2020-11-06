package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.EducType;
import com.glowbyteconsulting.resumebank.repository.EducTypeRepository;
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
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.EducType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EducTypeResource {

    private final Logger log = LoggerFactory.getLogger(EducTypeResource.class);

    private static final String ENTITY_NAME = "educType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducTypeRepository educTypeRepository;

    public EducTypeResource(EducTypeRepository educTypeRepository) {
        this.educTypeRepository = educTypeRepository;
    }

    /**
     * {@code POST  /educ-types} : Create a new educType.
     *
     * @param educType the educType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educType, or with status {@code 400 (Bad Request)} if the educType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educ-types")
    public ResponseEntity<EducType> createEducType(@RequestBody EducType educType) throws URISyntaxException {
        log.debug("REST request to save EducType : {}", educType);
        if (educType.getId() != null) {
            throw new BadRequestAlertException("A new educType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducType result = educTypeRepository.save(educType);
        return ResponseEntity.created(new URI("/api/educ-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /educ-types} : Updates an existing educType.
     *
     * @param educType the educType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educType,
     * or with status {@code 400 (Bad Request)} if the educType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educ-types")
    public ResponseEntity<EducType> updateEducType(@RequestBody EducType educType) throws URISyntaxException {
        log.debug("REST request to update EducType : {}", educType);
        if (educType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EducType result = educTypeRepository.save(educType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /educ-types} : get all the educTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educTypes in body.
     */
    @GetMapping("/educ-types")
    public List<EducType> getAllEducTypes() {
        log.debug("REST request to get all EducTypes");
        return educTypeRepository.findAll();
    }

    /**
     * {@code GET  /educ-types/:id} : get the "id" educType.
     *
     * @param id the id of the educType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educ-types/{id}")
    public ResponseEntity<EducType> getEducType(@PathVariable Long id) {
        log.debug("REST request to get EducType : {}", id);
        Optional<EducType> educType = educTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(educType);
    }

    /**
     * {@code DELETE  /educ-types/:id} : delete the "id" educType.
     *
     * @param id the id of the educType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educ-types/{id}")
    public ResponseEntity<Void> deleteEducType(@PathVariable Long id) {
        log.debug("REST request to delete EducType : {}", id);
        educTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
