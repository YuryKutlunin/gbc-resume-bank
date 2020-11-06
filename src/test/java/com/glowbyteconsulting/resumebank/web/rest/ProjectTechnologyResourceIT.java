package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.ProjectTechnology;
import com.glowbyteconsulting.resumebank.repository.ProjectTechnologyRepository;

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
 * Integration tests for the {@link ProjectTechnologyResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjectTechnologyResourceIT {

    private static final Long DEFAULT_ID_PROJECT = 1L;
    private static final Long UPDATED_ID_PROJECT = 2L;

    private static final Long DEFAULT_ID_TECHNOLOGY = 1L;
    private static final Long UPDATED_ID_TECHNOLOGY = 2L;

    @Autowired
    private ProjectTechnologyRepository projectTechnologyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectTechnologyMockMvc;

    private ProjectTechnology projectTechnology;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTechnology createEntity(EntityManager em) {
        ProjectTechnology projectTechnology = new ProjectTechnology()
            .idProject(DEFAULT_ID_PROJECT)
            .idTechnology(DEFAULT_ID_TECHNOLOGY);
        return projectTechnology;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTechnology createUpdatedEntity(EntityManager em) {
        ProjectTechnology projectTechnology = new ProjectTechnology()
            .idProject(UPDATED_ID_PROJECT)
            .idTechnology(UPDATED_ID_TECHNOLOGY);
        return projectTechnology;
    }

    @BeforeEach
    public void initTest() {
        projectTechnology = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectTechnology() throws Exception {
        int databaseSizeBeforeCreate = projectTechnologyRepository.findAll().size();
        // Create the ProjectTechnology
        restProjectTechnologyMockMvc.perform(post("/api/project-technologies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTechnology)))
            .andExpect(status().isCreated());

        // Validate the ProjectTechnology in the database
        List<ProjectTechnology> projectTechnologyList = projectTechnologyRepository.findAll();
        assertThat(projectTechnologyList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTechnology testProjectTechnology = projectTechnologyList.get(projectTechnologyList.size() - 1);
        assertThat(testProjectTechnology.getIdProject()).isEqualTo(DEFAULT_ID_PROJECT);
        assertThat(testProjectTechnology.getIdTechnology()).isEqualTo(DEFAULT_ID_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void createProjectTechnologyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectTechnologyRepository.findAll().size();

        // Create the ProjectTechnology with an existing ID
        projectTechnology.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTechnologyMockMvc.perform(post("/api/project-technologies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTechnology)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTechnology in the database
        List<ProjectTechnology> projectTechnologyList = projectTechnologyRepository.findAll();
        assertThat(projectTechnologyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectTechnologies() throws Exception {
        // Initialize the database
        projectTechnologyRepository.saveAndFlush(projectTechnology);

        // Get all the projectTechnologyList
        restProjectTechnologyMockMvc.perform(get("/api/project-technologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTechnology.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProject").value(hasItem(DEFAULT_ID_PROJECT.intValue())))
            .andExpect(jsonPath("$.[*].idTechnology").value(hasItem(DEFAULT_ID_TECHNOLOGY.intValue())));
    }
    
    @Test
    @Transactional
    public void getProjectTechnology() throws Exception {
        // Initialize the database
        projectTechnologyRepository.saveAndFlush(projectTechnology);

        // Get the projectTechnology
        restProjectTechnologyMockMvc.perform(get("/api/project-technologies/{id}", projectTechnology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectTechnology.getId().intValue()))
            .andExpect(jsonPath("$.idProject").value(DEFAULT_ID_PROJECT.intValue()))
            .andExpect(jsonPath("$.idTechnology").value(DEFAULT_ID_TECHNOLOGY.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingProjectTechnology() throws Exception {
        // Get the projectTechnology
        restProjectTechnologyMockMvc.perform(get("/api/project-technologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectTechnology() throws Exception {
        // Initialize the database
        projectTechnologyRepository.saveAndFlush(projectTechnology);

        int databaseSizeBeforeUpdate = projectTechnologyRepository.findAll().size();

        // Update the projectTechnology
        ProjectTechnology updatedProjectTechnology = projectTechnologyRepository.findById(projectTechnology.getId()).get();
        // Disconnect from session so that the updates on updatedProjectTechnology are not directly saved in db
        em.detach(updatedProjectTechnology);
        updatedProjectTechnology
            .idProject(UPDATED_ID_PROJECT)
            .idTechnology(UPDATED_ID_TECHNOLOGY);

        restProjectTechnologyMockMvc.perform(put("/api/project-technologies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectTechnology)))
            .andExpect(status().isOk());

        // Validate the ProjectTechnology in the database
        List<ProjectTechnology> projectTechnologyList = projectTechnologyRepository.findAll();
        assertThat(projectTechnologyList).hasSize(databaseSizeBeforeUpdate);
        ProjectTechnology testProjectTechnology = projectTechnologyList.get(projectTechnologyList.size() - 1);
        assertThat(testProjectTechnology.getIdProject()).isEqualTo(UPDATED_ID_PROJECT);
        assertThat(testProjectTechnology.getIdTechnology()).isEqualTo(UPDATED_ID_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectTechnology() throws Exception {
        int databaseSizeBeforeUpdate = projectTechnologyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTechnologyMockMvc.perform(put("/api/project-technologies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectTechnology)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTechnology in the database
        List<ProjectTechnology> projectTechnologyList = projectTechnologyRepository.findAll();
        assertThat(projectTechnologyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectTechnology() throws Exception {
        // Initialize the database
        projectTechnologyRepository.saveAndFlush(projectTechnology);

        int databaseSizeBeforeDelete = projectTechnologyRepository.findAll().size();

        // Delete the projectTechnology
        restProjectTechnologyMockMvc.perform(delete("/api/project-technologies/{id}", projectTechnology.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectTechnology> projectTechnologyList = projectTechnologyRepository.findAll();
        assertThat(projectTechnologyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
