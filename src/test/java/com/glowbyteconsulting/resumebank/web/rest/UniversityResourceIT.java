package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.University;
import com.glowbyteconsulting.resumebank.repository.UniversityRepository;

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
 * Integration tests for the {@link UniversityResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UniversityResourceIT {

    private static final Long DEFAULT_ID_UNIVER = 1L;
    private static final Long UPDATED_ID_UNIVER = 2L;

    private static final String DEFAULT_UNIVER_NM = "AAAAAAAAAA";
    private static final String UPDATED_UNIVER_NM = "BBBBBBBBBB";

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUniversityMockMvc;

    private University university;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static University createEntity(EntityManager em) {
        University university = new University()
            .idUniver(DEFAULT_ID_UNIVER)
            .univerNm(DEFAULT_UNIVER_NM);
        return university;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static University createUpdatedEntity(EntityManager em) {
        University university = new University()
            .idUniver(UPDATED_ID_UNIVER)
            .univerNm(UPDATED_UNIVER_NM);
        return university;
    }

    @BeforeEach
    public void initTest() {
        university = createEntity(em);
    }

    @Test
    @Transactional
    public void createUniversity() throws Exception {
        int databaseSizeBeforeCreate = universityRepository.findAll().size();
        // Create the University
        restUniversityMockMvc.perform(post("/api/universities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(university)))
            .andExpect(status().isCreated());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeCreate + 1);
        University testUniversity = universityList.get(universityList.size() - 1);
        assertThat(testUniversity.getIdUniver()).isEqualTo(DEFAULT_ID_UNIVER);
        assertThat(testUniversity.getUniverNm()).isEqualTo(DEFAULT_UNIVER_NM);
    }

    @Test
    @Transactional
    public void createUniversityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = universityRepository.findAll().size();

        // Create the University with an existing ID
        university.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniversityMockMvc.perform(post("/api/universities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(university)))
            .andExpect(status().isBadRequest());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUniversities() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        // Get all the universityList
        restUniversityMockMvc.perform(get("/api/universities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(university.getId().intValue())))
            .andExpect(jsonPath("$.[*].idUniver").value(hasItem(DEFAULT_ID_UNIVER.intValue())))
            .andExpect(jsonPath("$.[*].univerNm").value(hasItem(DEFAULT_UNIVER_NM)));
    }
    
    @Test
    @Transactional
    public void getUniversity() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        // Get the university
        restUniversityMockMvc.perform(get("/api/universities/{id}", university.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(university.getId().intValue()))
            .andExpect(jsonPath("$.idUniver").value(DEFAULT_ID_UNIVER.intValue()))
            .andExpect(jsonPath("$.univerNm").value(DEFAULT_UNIVER_NM));
    }
    @Test
    @Transactional
    public void getNonExistingUniversity() throws Exception {
        // Get the university
        restUniversityMockMvc.perform(get("/api/universities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUniversity() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        int databaseSizeBeforeUpdate = universityRepository.findAll().size();

        // Update the university
        University updatedUniversity = universityRepository.findById(university.getId()).get();
        // Disconnect from session so that the updates on updatedUniversity are not directly saved in db
        em.detach(updatedUniversity);
        updatedUniversity
            .idUniver(UPDATED_ID_UNIVER)
            .univerNm(UPDATED_UNIVER_NM);

        restUniversityMockMvc.perform(put("/api/universities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUniversity)))
            .andExpect(status().isOk());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
        University testUniversity = universityList.get(universityList.size() - 1);
        assertThat(testUniversity.getIdUniver()).isEqualTo(UPDATED_ID_UNIVER);
        assertThat(testUniversity.getUniverNm()).isEqualTo(UPDATED_UNIVER_NM);
    }

    @Test
    @Transactional
    public void updateNonExistingUniversity() throws Exception {
        int databaseSizeBeforeUpdate = universityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversityMockMvc.perform(put("/api/universities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(university)))
            .andExpect(status().isBadRequest());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUniversity() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        int databaseSizeBeforeDelete = universityRepository.findAll().size();

        // Delete the university
        restUniversityMockMvc.perform(delete("/api/universities/{id}", university.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
