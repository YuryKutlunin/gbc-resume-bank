package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.EmployeeProject;
import com.glowbyteconsulting.resumebank.repository.EmployeeProjectRepository;

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
 * Integration tests for the {@link EmployeeProjectResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeProjectResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_PROJECT = 1L;
    private static final Long UPDATED_ID_PROJECT = 2L;

    private static final Long DEFAULT_ID_ROLE = 1L;
    private static final Long UPDATED_ID_ROLE = 2L;

    private static final String DEFAULT_RESPONSIBILITY_NM = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBILITY_NM = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeProjectMockMvc;

    private EmployeeProject employeeProject;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProject createEntity(EntityManager em) {
        EmployeeProject employeeProject = new EmployeeProject()
            .email(DEFAULT_EMAIL)
            .idProject(DEFAULT_ID_PROJECT)
            .idRole(DEFAULT_ID_ROLE)
            .responsibilityNm(DEFAULT_RESPONSIBILITY_NM)
            .startDt(DEFAULT_START_DT)
            .endDt(DEFAULT_END_DT);
        return employeeProject;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeProject createUpdatedEntity(EntityManager em) {
        EmployeeProject employeeProject = new EmployeeProject()
            .email(UPDATED_EMAIL)
            .idProject(UPDATED_ID_PROJECT)
            .idRole(UPDATED_ID_ROLE)
            .responsibilityNm(UPDATED_RESPONSIBILITY_NM)
            .startDt(UPDATED_START_DT)
            .endDt(UPDATED_END_DT);
        return employeeProject;
    }

    @BeforeEach
    public void initTest() {
        employeeProject = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeProject() throws Exception {
        int databaseSizeBeforeCreate = employeeProjectRepository.findAll().size();
        // Create the EmployeeProject
        restEmployeeProjectMockMvc.perform(post("/api/employee-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeProject)))
            .andExpect(status().isCreated());

        // Validate the EmployeeProject in the database
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeProject testEmployeeProject = employeeProjectList.get(employeeProjectList.size() - 1);
        assertThat(testEmployeeProject.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployeeProject.getIdProject()).isEqualTo(DEFAULT_ID_PROJECT);
        assertThat(testEmployeeProject.getIdRole()).isEqualTo(DEFAULT_ID_ROLE);
        assertThat(testEmployeeProject.getResponsibilityNm()).isEqualTo(DEFAULT_RESPONSIBILITY_NM);
        assertThat(testEmployeeProject.getStartDt()).isEqualTo(DEFAULT_START_DT);
        assertThat(testEmployeeProject.getEndDt()).isEqualTo(DEFAULT_END_DT);
    }

    @Test
    @Transactional
    public void createEmployeeProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeProjectRepository.findAll().size();

        // Create the EmployeeProject with an existing ID
        employeeProject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeProjectMockMvc.perform(post("/api/employee-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeProject)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProject in the database
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeProjects() throws Exception {
        // Initialize the database
        employeeProjectRepository.saveAndFlush(employeeProject);

        // Get all the employeeProjectList
        restEmployeeProjectMockMvc.perform(get("/api/employee-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProject.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].idProject").value(hasItem(DEFAULT_ID_PROJECT.intValue())))
            .andExpect(jsonPath("$.[*].idRole").value(hasItem(DEFAULT_ID_ROLE.intValue())))
            .andExpect(jsonPath("$.[*].responsibilityNm").value(hasItem(DEFAULT_RESPONSIBILITY_NM)))
            .andExpect(jsonPath("$.[*].startDt").value(hasItem(DEFAULT_START_DT.toString())))
            .andExpect(jsonPath("$.[*].endDt").value(hasItem(DEFAULT_END_DT.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeeProject() throws Exception {
        // Initialize the database
        employeeProjectRepository.saveAndFlush(employeeProject);

        // Get the employeeProject
        restEmployeeProjectMockMvc.perform(get("/api/employee-projects/{id}", employeeProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeProject.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.idProject").value(DEFAULT_ID_PROJECT.intValue()))
            .andExpect(jsonPath("$.idRole").value(DEFAULT_ID_ROLE.intValue()))
            .andExpect(jsonPath("$.responsibilityNm").value(DEFAULT_RESPONSIBILITY_NM))
            .andExpect(jsonPath("$.startDt").value(DEFAULT_START_DT.toString()))
            .andExpect(jsonPath("$.endDt").value(DEFAULT_END_DT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmployeeProject() throws Exception {
        // Get the employeeProject
        restEmployeeProjectMockMvc.perform(get("/api/employee-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeProject() throws Exception {
        // Initialize the database
        employeeProjectRepository.saveAndFlush(employeeProject);

        int databaseSizeBeforeUpdate = employeeProjectRepository.findAll().size();

        // Update the employeeProject
        EmployeeProject updatedEmployeeProject = employeeProjectRepository.findById(employeeProject.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeProject are not directly saved in db
        em.detach(updatedEmployeeProject);
        updatedEmployeeProject
            .email(UPDATED_EMAIL)
            .idProject(UPDATED_ID_PROJECT)
            .idRole(UPDATED_ID_ROLE)
            .responsibilityNm(UPDATED_RESPONSIBILITY_NM)
            .startDt(UPDATED_START_DT)
            .endDt(UPDATED_END_DT);

        restEmployeeProjectMockMvc.perform(put("/api/employee-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeProject)))
            .andExpect(status().isOk());

        // Validate the EmployeeProject in the database
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProject testEmployeeProject = employeeProjectList.get(employeeProjectList.size() - 1);
        assertThat(testEmployeeProject.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployeeProject.getIdProject()).isEqualTo(UPDATED_ID_PROJECT);
        assertThat(testEmployeeProject.getIdRole()).isEqualTo(UPDATED_ID_ROLE);
        assertThat(testEmployeeProject.getResponsibilityNm()).isEqualTo(UPDATED_RESPONSIBILITY_NM);
        assertThat(testEmployeeProject.getStartDt()).isEqualTo(UPDATED_START_DT);
        assertThat(testEmployeeProject.getEndDt()).isEqualTo(UPDATED_END_DT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeProject() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeProjectMockMvc.perform(put("/api/employee-projects")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeProject)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProject in the database
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeProject() throws Exception {
        // Initialize the database
        employeeProjectRepository.saveAndFlush(employeeProject);

        int databaseSizeBeforeDelete = employeeProjectRepository.findAll().size();

        // Delete the employeeProject
        restEmployeeProjectMockMvc.perform(delete("/api/employee-projects/{id}", employeeProject.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
