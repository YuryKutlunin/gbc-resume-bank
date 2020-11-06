package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.SkillLevel;
import com.glowbyteconsulting.resumebank.repository.SkillLevelRepository;
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
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.SkillLevel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SkillLevelResource {

    private final Logger log = LoggerFactory.getLogger(SkillLevelResource.class);

    private static final String ENTITY_NAME = "skillLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillLevelRepository skillLevelRepository;

    public SkillLevelResource(SkillLevelRepository skillLevelRepository) {
        this.skillLevelRepository = skillLevelRepository;
    }

    /**
     * {@code POST  /skill-levels} : Create a new skillLevel.
     *
     * @param skillLevel the skillLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillLevel, or with status {@code 400 (Bad Request)} if the skillLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skill-levels")
    public ResponseEntity<SkillLevel> createSkillLevel(@RequestBody SkillLevel skillLevel) throws URISyntaxException {
        log.debug("REST request to save SkillLevel : {}", skillLevel);
        if (skillLevel.getId() != null) {
            throw new BadRequestAlertException("A new skillLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillLevel result = skillLevelRepository.save(skillLevel);
        return ResponseEntity.created(new URI("/api/skill-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-levels} : Updates an existing skillLevel.
     *
     * @param skillLevel the skillLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillLevel,
     * or with status {@code 400 (Bad Request)} if the skillLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skill-levels")
    public ResponseEntity<SkillLevel> updateSkillLevel(@RequestBody SkillLevel skillLevel) throws URISyntaxException {
        log.debug("REST request to update SkillLevel : {}", skillLevel);
        if (skillLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillLevel result = skillLevelRepository.save(skillLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skill-levels} : get all the skillLevels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillLevels in body.
     */
    @GetMapping("/skill-levels")
    public List<SkillLevel> getAllSkillLevels() {
        log.debug("REST request to get all SkillLevels");
        return skillLevelRepository.findAll();
    }

    /**
     * {@code GET  /skill-levels/:id} : get the "id" skillLevel.
     *
     * @param id the id of the skillLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skill-levels/{id}")
    public ResponseEntity<SkillLevel> getSkillLevel(@PathVariable Long id) {
        log.debug("REST request to get SkillLevel : {}", id);
        Optional<SkillLevel> skillLevel = skillLevelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(skillLevel);
    }

    /**
     * {@code DELETE  /skill-levels/:id} : delete the "id" skillLevel.
     *
     * @param id the id of the skillLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skill-levels/{id}")
    public ResponseEntity<Void> deleteSkillLevel(@PathVariable Long id) {
        log.debug("REST request to delete SkillLevel : {}", id);
        skillLevelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
