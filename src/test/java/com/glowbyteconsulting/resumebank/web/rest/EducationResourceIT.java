package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.Education;
import com.glowbyteconsulting.resumebank.repository.EducationRepository;

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
 * Integration tests for the {@link EducationResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EducationResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_UNIVER = 1L;
    private static final Long UPDATED_ID_UNIVER = 2L;

    private static final String DEFAULT_ID_EDUC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ID_EDUC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FACULTY = "AAAAAAAAAA";
    private static final String UPDATED_FACULTY = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALTY = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALTY = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALIZATION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALIZATION = "BBBBBBBBBB";

    private static final Long DEFAULT_START_YEAR = 1L;
    private static final Long UPDATED_START_YEAR = 2L;

    private static final Long DEFAULT_END_YEAR = 1L;
    private static final Long UPDATED_END_YEAR = 2L;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationMockMvc;

    private Education education;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createEntity(EntityManager em) {
        Education education = new Education()
            .email(DEFAULT_EMAIL)
            .idUniver(DEFAULT_ID_UNIVER)
            .idEducType(DEFAULT_ID_EDUC_TYPE)
            .faculty(DEFAULT_FACULTY)
            .specialty(DEFAULT_SPECIALTY)
            .specialization(DEFAULT_SPECIALIZATION)
            .startYear(DEFAULT_START_YEAR)
            .endYear(DEFAULT_END_YEAR);
        return education;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createUpdatedEntity(EntityManager em) {
        Education education = new Education()
            .email(UPDATED_EMAIL)
            .idUniver(UPDATED_ID_UNIVER)
            .idEducType(UPDATED_ID_EDUC_TYPE)
            .faculty(UPDATED_FACULTY)
            .specialty(UPDATED_SPECIALTY)
            .specialization(UPDATED_SPECIALIZATION)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR);
        return education;
    }

    @BeforeEach
    public void initTest() {
        education = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducation() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();
        // Create the Education
        restEducationMockMvc.perform(post("/api/educations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(education)))
            .andExpect(status().isCreated());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate + 1);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEducation.getIdUniver()).isEqualTo(DEFAULT_ID_UNIVER);
        assertThat(testEducation.getIdEducType()).isEqualTo(DEFAULT_ID_EDUC_TYPE);
        assertThat(testEducation.getFaculty()).isEqualTo(DEFAULT_FACULTY);
        assertThat(testEducation.getSpecialty()).isEqualTo(DEFAULT_SPECIALTY);
        assertThat(testEducation.getSpecialization()).isEqualTo(DEFAULT_SPECIALIZATION);
        assertThat(testEducation.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testEducation.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
    }

    @Test
    @Transactional
    public void createEducationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // Create the Education with an existing ID
        education.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationMockMvc.perform(post("/api/educations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(education)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEducations() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList
        restEducationMockMvc.perform(get("/api/educations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].idUniver").value(hasItem(DEFAULT_ID_UNIVER.intValue())))
            .andExpect(jsonPath("$.[*].idEducType").value(hasItem(DEFAULT_ID_EDUC_TYPE)))
            .andExpect(jsonPath("$.[*].faculty").value(hasItem(DEFAULT_FACULTY)))
            .andExpect(jsonPath("$.[*].specialty").value(hasItem(DEFAULT_SPECIALTY)))
            .andExpect(jsonPath("$.[*].specialization").value(hasItem(DEFAULT_SPECIALIZATION)))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR.intValue())));
    }
    
    @Test
    @Transactional
    public void getEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get the education
        restEducationMockMvc.perform(get("/api/educations/{id}", education.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(education.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.idUniver").value(DEFAULT_ID_UNIVER.intValue()))
            .andExpect(jsonPath("$.idEducType").value(DEFAULT_ID_EDUC_TYPE))
            .andExpect(jsonPath("$.faculty").value(DEFAULT_FACULTY))
            .andExpect(jsonPath("$.specialty").value(DEFAULT_SPECIALTY))
            .andExpect(jsonPath("$.specialization").value(DEFAULT_SPECIALIZATION))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR.intValue()))
            .andExpect(jsonPath("$.endYear").value(DEFAULT_END_YEAR.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingEducation() throws Exception {
        // Get the education
        restEducationMockMvc.perform(get("/api/educations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education
        Education updatedEducation = educationRepository.findById(education.getId()).get();
        // Disconnect from session so that the updates on updatedEducation are not directly saved in db
        em.detach(updatedEducation);
        updatedEducation
            .email(UPDATED_EMAIL)
            .idUniver(UPDATED_ID_UNIVER)
            .idEducType(UPDATED_ID_EDUC_TYPE)
            .faculty(UPDATED_FACULTY)
            .specialty(UPDATED_SPECIALTY)
            .specialization(UPDATED_SPECIALIZATION)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR);

        restEducationMockMvc.perform(put("/api/educations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEducation)))
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEducation.getIdUniver()).isEqualTo(UPDATED_ID_UNIVER);
        assertThat(testEducation.getIdEducType()).isEqualTo(UPDATED_ID_EDUC_TYPE);
        assertThat(testEducation.getFaculty()).isEqualTo(UPDATED_FACULTY);
        assertThat(testEducation.getSpecialty()).isEqualTo(UPDATED_SPECIALTY);
        assertThat(testEducation.getSpecialization()).isEqualTo(UPDATED_SPECIALIZATION);
        assertThat(testEducation.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testEducation.getEndYear()).isEqualTo(UPDATED_END_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc.perform(put("/api/educations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(education)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeDelete = educationRepository.findAll().size();

        // Delete the education
        restEducationMockMvc.perform(delete("/api/educations/{id}", education.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
