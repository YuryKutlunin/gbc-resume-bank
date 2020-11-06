package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.EmployeeCertif;
import com.glowbyteconsulting.resumebank.repository.EmployeeCertifRepository;
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
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.EmployeeCertif}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmployeeCertifResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeCertifResource.class);

    private static final String ENTITY_NAME = "employeeCertif";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeCertifRepository employeeCertifRepository;

    public EmployeeCertifResource(EmployeeCertifRepository employeeCertifRepository) {
        this.employeeCertifRepository = employeeCertifRepository;
    }

    /**
     * {@code POST  /employee-certifs} : Create a new employeeCertif.
     *
     * @param employeeCertif the employeeCertif to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeCertif, or with status {@code 400 (Bad Request)} if the employeeCertif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-certifs")
    public ResponseEntity<EmployeeCertif> createEmployeeCertif(@RequestBody EmployeeCertif employeeCertif) throws URISyntaxException {
        log.debug("REST request to save EmployeeCertif : {}", employeeCertif);
        if (employeeCertif.getId() != null) {
            throw new BadRequestAlertException("A new employeeCertif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeCertif result = employeeCertifRepository.save(employeeCertif);
        return ResponseEntity.created(new URI("/api/employee-certifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-certifs} : Updates an existing employeeCertif.
     *
     * @param employeeCertif the employeeCertif to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeCertif,
     * or with status {@code 400 (Bad Request)} if the employeeCertif is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeCertif couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-certifs")
    public ResponseEntity<EmployeeCertif> updateEmployeeCertif(@RequestBody EmployeeCertif employeeCertif) throws URISyntaxException {
        log.debug("REST request to update EmployeeCertif : {}", employeeCertif);
        if (employeeCertif.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeCertif result = employeeCertifRepository.save(employeeCertif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeCertif.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-certifs} : get all the employeeCertifs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeCertifs in body.
     */
    @GetMapping("/employee-certifs")
    public List<EmployeeCertif> getAllEmployeeCertifs() {
        log.debug("REST request to get all EmployeeCertifs");
        return employeeCertifRepository.findAll();
    }

    /**
     * {@code GET  /employee-certifs/:id} : get the "id" employeeCertif.
     *
     * @param id the id of the employeeCertif to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeCertif, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-certifs/{id}")
    public ResponseEntity<EmployeeCertif> getEmployeeCertif(@PathVariable Long id) {
        log.debug("REST request to get EmployeeCertif : {}", id);
        Optional<EmployeeCertif> employeeCertif = employeeCertifRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employeeCertif);
    }

    /**
     * {@code DELETE  /employee-certifs/:id} : delete the "id" employeeCertif.
     *
     * @param id the id of the employeeCertif to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-certifs/{id}")
    public ResponseEntity<Void> deleteEmployeeCertif(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeCertif : {}", id);
        employeeCertifRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
