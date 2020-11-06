package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.ResourcePool;
import com.glowbyteconsulting.resumebank.repository.ResourcePoolRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResourcePoolResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResourcePoolResourceIT {

    private static final String DEFAULT_RESOURCE_POOL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_POOL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE_POOL_NM = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_POOL_NM = "BBBBBBBBBB";

    private static final String DEFAULT_POOL_LEADER = "AAAAAAAAAA";
    private static final String UPDATED_POOL_LEADER = "BBBBBBBBBB";

    @Autowired
    private ResourcePoolRepository resourcePoolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResourcePoolMockMvc;

    private ResourcePool resourcePool;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourcePool createEntity(EntityManager em) {
        ResourcePool resourcePool = new ResourcePool()
            .resourcePoolCode(DEFAULT_RESOURCE_POOL_CODE)
            .resourcePoolNm(DEFAULT_RESOURCE_POOL_NM)
            .poolLeader(DEFAULT_POOL_LEADER);
        return resourcePool;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourcePool createUpdatedEntity(EntityManager em) {
        ResourcePool resourcePool = new ResourcePool()
            .resourcePoolCode(UPDATED_RESOURCE_POOL_CODE)
            .resourcePoolNm(UPDATED_RESOURCE_POOL_NM)
            .poolLeader(UPDATED_POOL_LEADER);
        return resourcePool;
    }

    @BeforeEach
    public void initTest() {
        resourcePool = createEntity(em);
    }

    @Test
    @Transactional
    public void createResourcePool() throws Exception {
        int databaseSizeBeforeCreate = resourcePoolRepository.findAll().size();
        // Create the ResourcePool
        restResourcePoolMockMvc.perform(post("/api/resource-pools")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcePool)))
            .andExpect(status().isCreated());

        // Validate the ResourcePool in the database
        List<ResourcePool> resourcePoolList = resourcePoolRepository.findAll();
        assertThat(resourcePoolList).hasSize(databaseSizeBeforeCreate + 1);
        ResourcePool testResourcePool = resourcePoolList.get(resourcePoolList.size() - 1);
        assertThat(testResourcePool.getResourcePoolCode()).isEqualTo(DEFAULT_RESOURCE_POOL_CODE);
        assertThat(testResourcePool.getResourcePoolNm()).isEqualTo(DEFAULT_RESOURCE_POOL_NM);
        assertThat(testResourcePool.getPoolLeader()).isEqualTo(DEFAULT_POOL_LEADER);
    }

    @Test
    @Transactional
    public void createResourcePoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourcePoolRepository.findAll().size();

        // Create the ResourcePool with an existing ID
        resourcePool.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourcePoolMockMvc.perform(post("/api/resource-pools")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcePool)))
            .andExpect(status().isBadRequest());

        // Validate the ResourcePool in the database
        List<ResourcePool> resourcePoolList = resourcePoolRepository.findAll();
        assertThat(resourcePoolList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResourcePools() throws Exception {
        // Initialize the database
        resourcePoolRepository.saveAndFlush(resourcePool);

        // Get all the resourcePoolList
        restResourcePoolMockMvc.perform(get("/api/resource-pools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourcePool.getId().intValue())))
            .andExpect(jsonPath("$.[*].resourcePoolCode").value(hasItem(DEFAULT_RESOURCE_POOL_CODE)))
            .andExpect(jsonPath("$.[*].resourcePoolNm").value(hasItem(DEFAULT_RESOURCE_POOL_NM)))
            .andExpect(jsonPath("$.[*].poolLeader").value(hasItem(DEFAULT_POOL_LEADER)));
    }
    
    @Test
    @Transactional
    public void getResourcePool() throws Exception {
        // Initialize the database
        resourcePoolRepository.saveAndFlush(resourcePool);

        // Get the resourcePool
        restResourcePoolMockMvc.perform(get("/api/resource-pools/{id}", resourcePool.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resourcePool.getId().intValue()))
            .andExpect(jsonPath("$.resourcePoolCode").value(DEFAULT_RESOURCE_POOL_CODE))
            .andExpect(jsonPath("$.resourcePoolNm").value(DEFAULT_RESOURCE_POOL_NM))
            .andExpect(jsonPath("$.poolLeader").value(DEFAULT_POOL_LEADER));
    }
    @Test
    @Transactional
    public void getNonExistingResourcePool() throws Exception {
        // Get the resourcePool
        restResourcePoolMockMvc.perform(get("/api/resource-pools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResourcePool() throws Exception {
        // Initialize the database
        resourcePoolRepository.saveAndFlush(resourcePool);

        int databaseSizeBeforeUpdate = resourcePoolRepository.findAll().size();

        // Update the resourcePool
        ResourcePool updatedResourcePool = resourcePoolRepository.findById(resourcePool.getId()).get();
        // Disconnect from session so that the updates on updatedResourcePool are not directly saved in db
        em.detach(updatedResourcePool);
        updatedResourcePool
            .resourcePoolCode(UPDATED_RESOURCE_POOL_CODE)
            .resourcePoolNm(UPDATED_RESOURCE_POOL_NM)
            .poolLeader(UPDATED_POOL_LEADER);

        restResourcePoolMockMvc.perform(put("/api/resource-pools")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedResourcePool)))
            .andExpect(status().isOk());

        // Validate the ResourcePool in the database
        List<ResourcePool> resourcePoolList = resourcePoolRepository.findAll();
        assertThat(resourcePoolList).hasSize(databaseSizeBeforeUpdate);
        ResourcePool testResourcePool = resourcePoolList.get(resourcePoolList.size() - 1);
        assertThat(testResourcePool.getResourcePoolCode()).isEqualTo(UPDATED_RESOURCE_POOL_CODE);
        assertThat(testResourcePool.getResourcePoolNm()).isEqualTo(UPDATED_RESOURCE_POOL_NM);
        assertThat(testResourcePool.getPoolLeader()).isEqualTo(UPDATED_POOL_LEADER);
    }

    @Test
    @Transactional
    public void updateNonExistingResourcePool() throws Exception {
        int databaseSizeBeforeUpdate = resourcePoolRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourcePoolMockMvc.perform(put("/api/resource-pools")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resourcePool)))
            .andExpect(status().isBadRequest());

        // Validate the ResourcePool in the database
        List<ResourcePool> resourcePoolList = resourcePoolRepository.findAll();
        assertThat(resourcePoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResourcePool() throws Exception {
        // Initialize the database
        resourcePoolRepository.saveAndFlush(resourcePool);

        int databaseSizeBeforeDelete = resourcePoolRepository.findAll().size();

        // Delete the resourcePool
        restResourcePoolMockMvc.perform(delete("/api/resource-pools/{id}", resourcePool.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResourcePool> resourcePoolList = resourcePoolRepository.findAll();
        assertThat(resourcePoolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
