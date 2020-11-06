package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.EmployeeSkill;
import com.glowbyteconsulting.resumebank.repository.EmployeeSkillRepository;

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
 * Integration tests for the {@link EmployeeSkillResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeSkillResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_SKILL = 1L;
    private static final Long UPDATED_ID_SKILL = 2L;

    private static final Long DEFAULT_ID_LEVEL = 1L;
    private static final Long UPDATED_ID_LEVEL = 2L;

    @Autowired
    private EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeSkillMockMvc;

    private EmployeeSkill employeeSkill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSkill createEntity(EntityManager em) {
        EmployeeSkill employeeSkill = new EmployeeSkill()
            .email(DEFAULT_EMAIL)
            .idSkill(DEFAULT_ID_SKILL)
            .idLevel(DEFAULT_ID_LEVEL);
        return employeeSkill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSkill createUpdatedEntity(EntityManager em) {
        EmployeeSkill employeeSkill = new EmployeeSkill()
            .email(UPDATED_EMAIL)
            .idSkill(UPDATED_ID_SKILL)
            .idLevel(UPDATED_ID_LEVEL);
        return employeeSkill;
    }

    @BeforeEach
    public void initTest() {
        employeeSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeSkill() throws Exception {
        int databaseSizeBeforeCreate = employeeSkillRepository.findAll().size();
        // Create the EmployeeSkill
        restEmployeeSkillMockMvc.perform(post("/api/employee-skills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkill)))
            .andExpect(status().isCreated());

        // Validate the EmployeeSkill in the database
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSkill testEmployeeSkill = employeeSkillList.get(employeeSkillList.size() - 1);
        assertThat(testEmployeeSkill.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployeeSkill.getIdSkill()).isEqualTo(DEFAULT_ID_SKILL);
        assertThat(testEmployeeSkill.getIdLevel()).isEqualTo(DEFAULT_ID_LEVEL);
    }

    @Test
    @Transactional
    public void createEmployeeSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeSkillRepository.findAll().size();

        // Create the EmployeeSkill with an existing ID
        employeeSkill.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSkillMockMvc.perform(post("/api/employee-skills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkill)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkill in the database
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeSkills() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get all the employeeSkillList
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].idSkill").value(hasItem(DEFAULT_ID_SKILL.intValue())))
            .andExpect(jsonPath("$.[*].idLevel").value(hasItem(DEFAULT_ID_LEVEL.intValue())));
    }
    
    @Test
    @Transactional
    public void getEmployeeSkill() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        // Get the employeeSkill
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills/{id}", employeeSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeSkill.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.idSkill").value(DEFAULT_ID_SKILL.intValue()))
            .andExpect(jsonPath("$.idLevel").value(DEFAULT_ID_LEVEL.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingEmployeeSkill() throws Exception {
        // Get the employeeSkill
        restEmployeeSkillMockMvc.perform(get("/api/employee-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeSkill() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        int databaseSizeBeforeUpdate = employeeSkillRepository.findAll().size();

        // Update the employeeSkill
        EmployeeSkill updatedEmployeeSkill = employeeSkillRepository.findById(employeeSkill.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeSkill are not directly saved in db
        em.detach(updatedEmployeeSkill);
        updatedEmployeeSkill
            .email(UPDATED_EMAIL)
            .idSkill(UPDATED_ID_SKILL)
            .idLevel(UPDATED_ID_LEVEL);

        restEmployeeSkillMockMvc.perform(put("/api/employee-skills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeSkill)))
            .andExpect(status().isOk());

        // Validate the EmployeeSkill in the database
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSkill testEmployeeSkill = employeeSkillList.get(employeeSkillList.size() - 1);
        assertThat(testEmployeeSkill.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployeeSkill.getIdSkill()).isEqualTo(UPDATED_ID_SKILL);
        assertThat(testEmployeeSkill.getIdLevel()).isEqualTo(UPDATED_ID_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeSkill() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSkillMockMvc.perform(put("/api/employee-skills")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeSkill)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkill in the database
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeSkill() throws Exception {
        // Initialize the database
        employeeSkillRepository.saveAndFlush(employeeSkill);

        int databaseSizeBeforeDelete = employeeSkillRepository.findAll().size();

        // Delete the employeeSkill
        restEmployeeSkillMockMvc.perform(delete("/api/employee-skills/{id}", employeeSkill.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeSkill> employeeSkillList = employeeSkillRepository.findAll();
        assertThat(employeeSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
