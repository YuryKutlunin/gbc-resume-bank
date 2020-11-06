package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.ProjectRole;
import com.glowbyteconsulting.resumebank.repository.ProjectRoleRepository;

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
 * Integration tests for the {@link ProjectRoleResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProjectRoleResourceIT {

    private static final Long DEFAULT_ID_ROLE = 1L;
    private static final Long UPDATED_ID_ROLE = 2L;

    private static final String DEFAULT_ROLE_NM = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NM = "BBBBBBBBBB";

    @Autowired
    private ProjectRoleRepository projectRoleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectRoleMockMvc;

    private ProjectRole projectRole;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectRole createEntity(EntityManager em) {
        ProjectRole projectRole = new ProjectRole()
            .idRole(DEFAULT_ID_ROLE)
            .roleNm(DEFAULT_ROLE_NM);
        return projectRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectRole createUpdatedEntity(EntityManager em) {
        ProjectRole projectRole = new ProjectRole()
            .idRole(UPDATED_ID_ROLE)
            .roleNm(UPDATED_ROLE_NM);
        return projectRole;
    }

    @BeforeEach
    public void initTest() {
        projectRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectRole() throws Exception {
        int databaseSizeBeforeCreate = projectRoleRepository.findAll().size();
        // Create the ProjectRole
        restProjectRoleMockMvc.perform(post("/api/project-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectRole)))
            .andExpect(status().isCreated());

        // Validate the ProjectRole in the database
        List<ProjectRole> projectRoleList = projectRoleRepository.findAll();
        assertThat(projectRoleList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectRole testProjectRole = projectRoleList.get(projectRoleList.size() - 1);
        assertThat(testProjectRole.getIdRole()).isEqualTo(DEFAULT_ID_ROLE);
        assertThat(testProjectRole.getRoleNm()).isEqualTo(DEFAULT_ROLE_NM);
    }

    @Test
    @Transactional
    public void createProjectRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRoleRepository.findAll().size();

        // Create the ProjectRole with an existing ID
        projectRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectRoleMockMvc.perform(post("/api/project-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectRole)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectRole in the database
        List<ProjectRole> projectRoleList = projectRoleRepository.findAll();
        assertThat(projectRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectRoles() throws Exception {
        // Initialize the database
        projectRoleRepository.saveAndFlush(projectRole);

        // Get all the projectRoleList
        restProjectRoleMockMvc.perform(get("/api/project-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].idRole").value(hasItem(DEFAULT_ID_ROLE.intValue())))
            .andExpect(jsonPath("$.[*].roleNm").value(hasItem(DEFAULT_ROLE_NM)));
    }
    
    @Test
    @Transactional
    public void getProjectRole() throws Exception {
        // Initialize the database
        projectRoleRepository.saveAndFlush(projectRole);

        // Get the projectRole
        restProjectRoleMockMvc.perform(get("/api/project-roles/{id}", projectRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectRole.getId().intValue()))
            .andExpect(jsonPath("$.idRole").value(DEFAULT_ID_ROLE.intValue()))
            .andExpect(jsonPath("$.roleNm").value(DEFAULT_ROLE_NM));
    }
    @Test
    @Transactional
    public void getNonExistingProjectRole() throws Exception {
        // Get the projectRole
        restProjectRoleMockMvc.perform(get("/api/project-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectRole() throws Exception {
        // Initialize the database
        projectRoleRepository.saveAndFlush(projectRole);

        int databaseSizeBeforeUpdate = projectRoleRepository.findAll().size();

        // Update the projectRole
        ProjectRole updatedProjectRole = projectRoleRepository.findById(projectRole.getId()).get();
        // Disconnect from session so that the updates on updatedProjectRole are not directly saved in db
        em.detach(updatedProjectRole);
        updatedProjectRole
            .idRole(UPDATED_ID_ROLE)
            .roleNm(UPDATED_ROLE_NM);

        restProjectRoleMockMvc.perform(put("/api/project-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectRole)))
            .andExpect(status().isOk());

        // Validate the ProjectRole in the database
        List<ProjectRole> projectRoleList = projectRoleRepository.findAll();
        assertThat(projectRoleList).hasSize(databaseSizeBeforeUpdate);
        ProjectRole testProjectRole = projectRoleList.get(projectRoleList.size() - 1);
        assertThat(testProjectRole.getIdRole()).isEqualTo(UPDATED_ID_ROLE);
        assertThat(testProjectRole.getRoleNm()).isEqualTo(UPDATED_ROLE_NM);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectRole() throws Exception {
        int databaseSizeBeforeUpdate = projectRoleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectRoleMockMvc.perform(put("/api/project-roles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectRole)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectRole in the database
        List<ProjectRole> projectRoleList = projectRoleRepository.findAll();
        assertThat(projectRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectRole() throws Exception {
        // Initialize the database
        projectRoleRepository.saveAndFlush(projectRole);

        int databaseSizeBeforeDelete = projectRoleRepository.findAll().size();

        // Delete the projectRole
        restProjectRoleMockMvc.perform(delete("/api/project-roles/{id}", projectRole.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectRole> projectRoleList = projectRoleRepository.findAll();
        assertThat(projectRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
