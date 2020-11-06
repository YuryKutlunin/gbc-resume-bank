package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.EducType;
import com.glowbyteconsulting.resumebank.repository.EducTypeRepository;

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
 * Integration tests for the {@link EducTypeResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EducTypeResourceIT {

    private static final Long DEFAULT_ID_EDUC_TYPE = 1L;
    private static final Long UPDATED_ID_EDUC_TYPE = 2L;

    private static final String DEFAULT_EDUC_TYPE_NM = "AAAAAAAAAA";
    private static final String UPDATED_EDUC_TYPE_NM = "BBBBBBBBBB";

    @Autowired
    private EducTypeRepository educTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducTypeMockMvc;

    private EducType educType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducType createEntity(EntityManager em) {
        EducType educType = new EducType()
            .idEducType(DEFAULT_ID_EDUC_TYPE)
            .educTypeNm(DEFAULT_EDUC_TYPE_NM);
        return educType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EducType createUpdatedEntity(EntityManager em) {
        EducType educType = new EducType()
            .idEducType(UPDATED_ID_EDUC_TYPE)
            .educTypeNm(UPDATED_EDUC_TYPE_NM);
        return educType;
    }

    @BeforeEach
    public void initTest() {
        educType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducType() throws Exception {
        int databaseSizeBeforeCreate = educTypeRepository.findAll().size();
        // Create the EducType
        restEducTypeMockMvc.perform(post("/api/educ-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educType)))
            .andExpect(status().isCreated());

        // Validate the EducType in the database
        List<EducType> educTypeList = educTypeRepository.findAll();
        assertThat(educTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EducType testEducType = educTypeList.get(educTypeList.size() - 1);
        assertThat(testEducType.getIdEducType()).isEqualTo(DEFAULT_ID_EDUC_TYPE);
        assertThat(testEducType.getEducTypeNm()).isEqualTo(DEFAULT_EDUC_TYPE_NM);
    }

    @Test
    @Transactional
    public void createEducTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educTypeRepository.findAll().size();

        // Create the EducType with an existing ID
        educType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducTypeMockMvc.perform(post("/api/educ-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educType)))
            .andExpect(status().isBadRequest());

        // Validate the EducType in the database
        List<EducType> educTypeList = educTypeRepository.findAll();
        assertThat(educTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEducTypes() throws Exception {
        // Initialize the database
        educTypeRepository.saveAndFlush(educType);

        // Get all the educTypeList
        restEducTypeMockMvc.perform(get("/api/educ-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educType.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEducType").value(hasItem(DEFAULT_ID_EDUC_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].educTypeNm").value(hasItem(DEFAULT_EDUC_TYPE_NM)));
    }
    
    @Test
    @Transactional
    public void getEducType() throws Exception {
        // Initialize the database
        educTypeRepository.saveAndFlush(educType);

        // Get the educType
        restEducTypeMockMvc.perform(get("/api/educ-types/{id}", educType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(educType.getId().intValue()))
            .andExpect(jsonPath("$.idEducType").value(DEFAULT_ID_EDUC_TYPE.intValue()))
            .andExpect(jsonPath("$.educTypeNm").value(DEFAULT_EDUC_TYPE_NM));
    }
    @Test
    @Transactional
    public void getNonExistingEducType() throws Exception {
        // Get the educType
        restEducTypeMockMvc.perform(get("/api/educ-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducType() throws Exception {
        // Initialize the database
        educTypeRepository.saveAndFlush(educType);

        int databaseSizeBeforeUpdate = educTypeRepository.findAll().size();

        // Update the educType
        EducType updatedEducType = educTypeRepository.findById(educType.getId()).get();
        // Disconnect from session so that the updates on updatedEducType are not directly saved in db
        em.detach(updatedEducType);
        updatedEducType
            .idEducType(UPDATED_ID_EDUC_TYPE)
            .educTypeNm(UPDATED_EDUC_TYPE_NM);

        restEducTypeMockMvc.perform(put("/api/educ-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEducType)))
            .andExpect(status().isOk());

        // Validate the EducType in the database
        List<EducType> educTypeList = educTypeRepository.findAll();
        assertThat(educTypeList).hasSize(databaseSizeBeforeUpdate);
        EducType testEducType = educTypeList.get(educTypeList.size() - 1);
        assertThat(testEducType.getIdEducType()).isEqualTo(UPDATED_ID_EDUC_TYPE);
        assertThat(testEducType.getEducTypeNm()).isEqualTo(UPDATED_EDUC_TYPE_NM);
    }

    @Test
    @Transactional
    public void updateNonExistingEducType() throws Exception {
        int databaseSizeBeforeUpdate = educTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducTypeMockMvc.perform(put("/api/educ-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educType)))
            .andExpect(status().isBadRequest());

        // Validate the EducType in the database
        List<EducType> educTypeList = educTypeRepository.findAll();
        assertThat(educTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEducType() throws Exception {
        // Initialize the database
        educTypeRepository.saveAndFlush(educType);

        int databaseSizeBeforeDelete = educTypeRepository.findAll().size();

        // Delete the educType
        restEducTypeMockMvc.perform(delete("/api/educ-types/{id}", educType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EducType> educTypeList = educTypeRepository.findAll();
        assertThat(educTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
