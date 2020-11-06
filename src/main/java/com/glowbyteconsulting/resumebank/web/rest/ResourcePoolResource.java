package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.domain.ResourcePool;
import com.glowbyteconsulting.resumebank.repository.ResourcePoolRepository;
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
 * REST controller for managing {@link com.glowbyteconsulting.resumebank.domain.ResourcePool}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResourcePoolResource {

    private final Logger log = LoggerFactory.getLogger(ResourcePoolResource.class);

    private static final String ENTITY_NAME = "resourcePool";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResourcePoolRepository resourcePoolRepository;

    public ResourcePoolResource(ResourcePoolRepository resourcePoolRepository) {
        this.resourcePoolRepository = resourcePoolRepository;
    }

    /**
     * {@code POST  /resource-pools} : Create a new resourcePool.
     *
     * @param resourcePool the resourcePool to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resourcePool, or with status {@code 400 (Bad Request)} if the resourcePool has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resource-pools")
    public ResponseEntity<ResourcePool> createResourcePool(@RequestBody ResourcePool resourcePool) throws URISyntaxException {
        log.debug("REST request to save ResourcePool : {}", resourcePool);
        if (resourcePool.getId() != null) {
            throw new BadRequestAlertException("A new resourcePool cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResourcePool result = resourcePoolRepository.save(resourcePool);
        return ResponseEntity.created(new URI("/api/resource-pools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resource-pools} : Updates an existing resourcePool.
     *
     * @param resourcePool the resourcePool to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourcePool,
     * or with status {@code 400 (Bad Request)} if the resourcePool is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resourcePool couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resource-pools")
    public ResponseEntity<ResourcePool> updateResourcePool(@RequestBody ResourcePool resourcePool) throws URISyntaxException {
        log.debug("REST request to update ResourcePool : {}", resourcePool);
        if (resourcePool.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResourcePool result = resourcePoolRepository.save(resourcePool);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourcePool.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resource-pools} : get all the resourcePools.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resourcePools in body.
     */
    @GetMapping("/resource-pools")
    public List<ResourcePool> getAllResourcePools() {
        log.debug("REST request to get all ResourcePools");
        return resourcePoolRepository.findAll();
    }

    /**
     * {@code GET  /resource-pools/:id} : get the "id" resourcePool.
     *
     * @param id the id of the resourcePool to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resourcePool, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resource-pools/{id}")
    public ResponseEntity<ResourcePool> getResourcePool(@PathVariable Long id) {
        log.debug("REST request to get ResourcePool : {}", id);
        Optional<ResourcePool> resourcePool = resourcePoolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resourcePool);
    }

    /**
     * {@code DELETE  /resource-pools/:id} : delete the "id" resourcePool.
     *
     * @param id the id of the resourcePool to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resource-pools/{id}")
    public ResponseEntity<Void> deleteResourcePool(@PathVariable Long id) {
        log.debug("REST request to delete ResourcePool : {}", id);
        resourcePoolRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
