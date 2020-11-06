package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.JobTitle;
import com.glowbyteconsulting.resumebank.repository.JobTitleRepository;

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
 * Integration tests for the {@link JobTitleResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobTitleResourceIT {

    private static final Long DEFAULT_ID_TITLE = 1L;
    private static final Long UPDATED_ID_TITLE = 2L;

    private static final String DEFAULT_TITLE_NM = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_NM = "BBBBBBBBBB";

    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobTitleMockMvc;

    private JobTitle jobTitle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobTitle createEntity(EntityManager em) {
        JobTitle jobTitle = new JobTitle()
            .idTitle(DEFAULT_ID_TITLE)
            .titleNM(DEFAULT_TITLE_NM);
        return jobTitle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobTitle createUpdatedEntity(EntityManager em) {
        JobTitle jobTitle = new JobTitle()
            .idTitle(UPDATED_ID_TITLE)
            .titleNM(UPDATED_TITLE_NM);
        return jobTitle;
    }

    @BeforeEach
    public void initTest() {
        jobTitle = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobTitle() throws Exception {
        int databaseSizeBeforeCreate = jobTitleRepository.findAll().size();
        // Create the JobTitle
        restJobTitleMockMvc.perform(post("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobTitle)))
            .andExpect(status().isCreated());

        // Validate the JobTitle in the database
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeCreate + 1);
        JobTitle testJobTitle = jobTitleList.get(jobTitleList.size() - 1);
        assertThat(testJobTitle.getIdTitle()).isEqualTo(DEFAULT_ID_TITLE);
        assertThat(testJobTitle.getTitleNM()).isEqualTo(DEFAULT_TITLE_NM);
    }

    @Test
    @Transactional
    public void createJobTitleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobTitleRepository.findAll().size();

        // Create the JobTitle with an existing ID
        jobTitle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobTitleMockMvc.perform(post("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobTitle)))
            .andExpect(status().isBadRequest());

        // Validate the JobTitle in the database
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobTitles() throws Exception {
        // Initialize the database
        jobTitleRepository.saveAndFlush(jobTitle);

        // Get all the jobTitleList
        restJobTitleMockMvc.perform(get("/api/job-titles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobTitle.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTitle").value(hasItem(DEFAULT_ID_TITLE.intValue())))
            .andExpect(jsonPath("$.[*].titleNM").value(hasItem(DEFAULT_TITLE_NM)));
    }
    
    @Test
    @Transactional
    public void getJobTitle() throws Exception {
        // Initialize the database
        jobTitleRepository.saveAndFlush(jobTitle);

        // Get the jobTitle
        restJobTitleMockMvc.perform(get("/api/job-titles/{id}", jobTitle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobTitle.getId().intValue()))
            .andExpect(jsonPath("$.idTitle").value(DEFAULT_ID_TITLE.intValue()))
            .andExpect(jsonPath("$.titleNM").value(DEFAULT_TITLE_NM));
    }
    @Test
    @Transactional
    public void getNonExistingJobTitle() throws Exception {
        // Get the jobTitle
        restJobTitleMockMvc.perform(get("/api/job-titles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobTitle() throws Exception {
        // Initialize the database
        jobTitleRepository.saveAndFlush(jobTitle);

        int databaseSizeBeforeUpdate = jobTitleRepository.findAll().size();

        // Update the jobTitle
        JobTitle updatedJobTitle = jobTitleRepository.findById(jobTitle.getId()).get();
        // Disconnect from session so that the updates on updatedJobTitle are not directly saved in db
        em.detach(updatedJobTitle);
        updatedJobTitle
            .idTitle(UPDATED_ID_TITLE)
            .titleNM(UPDATED_TITLE_NM);

        restJobTitleMockMvc.perform(put("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobTitle)))
            .andExpect(status().isOk());

        // Validate the JobTitle in the database
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeUpdate);
        JobTitle testJobTitle = jobTitleList.get(jobTitleList.size() - 1);
        assertThat(testJobTitle.getIdTitle()).isEqualTo(UPDATED_ID_TITLE);
        assertThat(testJobTitle.getTitleNM()).isEqualTo(UPDATED_TITLE_NM);
    }

    @Test
    @Transactional
    public void updateNonExistingJobTitle() throws Exception {
        int databaseSizeBeforeUpdate = jobTitleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobTitleMockMvc.perform(put("/api/job-titles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobTitle)))
            .andExpect(status().isBadRequest());

        // Validate the JobTitle in the database
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobTitle() throws Exception {
        // Initialize the database
        jobTitleRepository.saveAndFlush(jobTitle);

        int databaseSizeBeforeDelete = jobTitleRepository.findAll().size();

        // Delete the jobTitle
        restJobTitleMockMvc.perform(delete("/api/job-titles/{id}", jobTitle.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobTitle> jobTitleList = jobTitleRepository.findAll();
        assertThat(jobTitleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
