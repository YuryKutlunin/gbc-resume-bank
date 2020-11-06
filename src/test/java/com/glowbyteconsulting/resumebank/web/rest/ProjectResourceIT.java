package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.Project;
import com.glowbyteconsulting.resumebank.repository.ProjectRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProjectResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjectResourceIT {

    private static final Long DEFAULT_ID_PROJECT = 1L;
    private static final Long UPDATED_ID_PROJECT = 2L;

    private static final String DEFAULT_PROJECT_NM = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NM = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NM = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NM = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectMockMvc;

    private Project project;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createEntity(EntityManager em) {
        Project project = new Project()
            .idProject(DEFAULT_ID_PROJECT)
            .projectNm(DEFAULT_PROJECT_NM)
            .companyNM(DEFAULT_COMPANY_NM)
            .startDt(DEFAULT_START_DT)
            .endDt(DEFAULT_END_DT);
        return project;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Project createUpdatedEntity(EntityManager em) {
        Project project = new Project()
            .idProject(UPDATED_ID_PROJECT)
            .projectNm(UPDATED_PROJECT_NM)
            .companyNM(UPDATED_COMPANY_NM)
            .startDt(UPDATED_START_DT)
            .endDt(UPDATED_END_DT);
        return project;
    }

    @BeforeEach
    public void initTest() {
        project = createEntity(em);
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();
        // Create the Project
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getIdProject()).isEqualTo(DEFAULT_ID_PROJECT);
        assertThat(testProject.getProjectNm()).isEqualTo(DEFAULT_PROJECT_NM);
        assertThat(testProject.getCompanyNM()).isEqualTo(DEFAULT_COMPANY_NM);
        assertThat(testProject.getStartDt()).isEqualTo(DEFAULT_START_DT);
        assertThat(testProject.getEndDt()).isEqualTo(DEFAULT_END_DT);
    }

    @Test
    @Transactional
    public void createProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project with an existing ID
        project.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projectList
        restProjectMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProject").value(hasItem(DEFAULT_ID_PROJECT.intValue())))
            .andExpect(jsonPath("$.[*].projectNm").value(hasItem(DEFAULT_PROJECT_NM)))
            .andExpect(jsonPath("$.[*].companyNM").value(hasItem(DEFAULT_COMPANY_NM)))
            .andExpect(jsonPath("$.[*].startDt").value(hasItem(DEFAULT_START_DT.toString())))
            .andExpect(jsonPath("$.[*].endDt").value(hasItem(DEFAULT_END_DT.toString())));
    }
    
    @Test
    @Transactional
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.idProject").value(DEFAULT_ID_PROJECT.intValue()))
            .andExpect(jsonPath("$.projectNm").value(DEFAULT_PROJECT_NM))
            .andExpect(jsonPath("$.companyNM").value(DEFAULT_COMPANY_NM))
            .andExpect(jsonPath("$.startDt").value(DEFAULT_START_DT.toString()))
            .andExpect(jsonPath("$.endDt").value(DEFAULT_END_DT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = projectRepository.findById(project.getId()).get();
        // Disconnect from session so that the updates on updatedProject are not directly saved in db
        em.detach(updatedProject);
        updatedProject
            .idProject(UPDATED_ID_PROJECT)
            .projectNm(UPDATED_PROJECT_NM)
            .companyNM(UPDATED_COMPANY_NM)
            .startDt(UPDATED_START_DT)
            .endDt(UPDATED_END_DT);

        restProjectMockMvc.perform(put("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProject)))
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projectList.get(projectList.size() - 1);
        assertThat(testProject.getIdProject()).isEqualTo(UPDATED_ID_PROJECT);
        assertThat(testProject.getProjectNm()).isEqualTo(UPDATED_PROJECT_NM);
        assertThat(testProject.getCompanyNM()).isEqualTo(UPDATED_COMPANY_NM);
        assertThat(testProject.getStartDt()).isEqualTo(UPDATED_START_DT);
        assertThat(testProject.getEndDt()).isEqualTo(UPDATED_END_DT);
    }

    @Test
    @Transactional
    public void updateNonExistingProject() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMockMvc.perform(put("/api/projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project in the database
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Delete the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Project> projectList = projectRepository.findAll();
        assertThat(projectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
