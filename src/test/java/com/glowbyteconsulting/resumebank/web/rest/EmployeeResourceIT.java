package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.Employee;
import com.glowbyteconsulting.resumebank.repository.EmployeeRepository;

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
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeResourceIT {

    private static final String DEFAULT_FIRST_NM = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NM = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NM = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NM = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NM = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTH_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_ID_TITLE = 1L;
    private static final Long UPDATED_ID_TITLE = 2L;

    private static final String DEFAULT_RESOURCE_POOL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE_POOL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CURATOR = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CURATOR = "BBBBBBBBBB";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .firstNm(DEFAULT_FIRST_NM)
            .lastNm(DEFAULT_LAST_NM)
            .middleNm(DEFAULT_MIDDLE_NM)
            .email(DEFAULT_EMAIL)
            .phoneNum(DEFAULT_PHONE_NUM)
            .workType(DEFAULT_WORK_TYPE)
            .birthDt(DEFAULT_BIRTH_DT)
            .idTitle(DEFAULT_ID_TITLE)
            .resourcePoolCode(DEFAULT_RESOURCE_POOL_CODE)
            .emailCurator(DEFAULT_EMAIL_CURATOR);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .firstNm(UPDATED_FIRST_NM)
            .lastNm(UPDATED_LAST_NM)
            .middleNm(UPDATED_MIDDLE_NM)
            .email(UPDATED_EMAIL)
            .phoneNum(UPDATED_PHONE_NUM)
            .workType(UPDATED_WORK_TYPE)
            .birthDt(UPDATED_BIRTH_DT)
            .idTitle(UPDATED_ID_TITLE)
            .resourcePoolCode(UPDATED_RESOURCE_POOL_CODE)
            .emailCurator(UPDATED_EMAIL_CURATOR);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstNm()).isEqualTo(DEFAULT_FIRST_NM);
        assertThat(testEmployee.getLastNm()).isEqualTo(DEFAULT_LAST_NM);
        assertThat(testEmployee.getMiddleNm()).isEqualTo(DEFAULT_MIDDLE_NM);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getPhoneNum()).isEqualTo(DEFAULT_PHONE_NUM);
        assertThat(testEmployee.getWorkType()).isEqualTo(DEFAULT_WORK_TYPE);
        assertThat(testEmployee.getBirthDt()).isEqualTo(DEFAULT_BIRTH_DT);
        assertThat(testEmployee.getIdTitle()).isEqualTo(DEFAULT_ID_TITLE);
        assertThat(testEmployee.getResourcePoolCode()).isEqualTo(DEFAULT_RESOURCE_POOL_CODE);
        assertThat(testEmployee.getEmailCurator()).isEqualTo(DEFAULT_EMAIL_CURATOR);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstNm").value(hasItem(DEFAULT_FIRST_NM)))
            .andExpect(jsonPath("$.[*].lastNm").value(hasItem(DEFAULT_LAST_NM)))
            .andExpect(jsonPath("$.[*].middleNm").value(hasItem(DEFAULT_MIDDLE_NM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].workType").value(hasItem(DEFAULT_WORK_TYPE)))
            .andExpect(jsonPath("$.[*].birthDt").value(hasItem(DEFAULT_BIRTH_DT.toString())))
            .andExpect(jsonPath("$.[*].idTitle").value(hasItem(DEFAULT_ID_TITLE.intValue())))
            .andExpect(jsonPath("$.[*].resourcePoolCode").value(hasItem(DEFAULT_RESOURCE_POOL_CODE)))
            .andExpect(jsonPath("$.[*].emailCurator").value(hasItem(DEFAULT_EMAIL_CURATOR)));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.firstNm").value(DEFAULT_FIRST_NM))
            .andExpect(jsonPath("$.lastNm").value(DEFAULT_LAST_NM))
            .andExpect(jsonPath("$.middleNm").value(DEFAULT_MIDDLE_NM))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNum").value(DEFAULT_PHONE_NUM))
            .andExpect(jsonPath("$.workType").value(DEFAULT_WORK_TYPE))
            .andExpect(jsonPath("$.birthDt").value(DEFAULT_BIRTH_DT.toString()))
            .andExpect(jsonPath("$.idTitle").value(DEFAULT_ID_TITLE.intValue()))
            .andExpect(jsonPath("$.resourcePoolCode").value(DEFAULT_RESOURCE_POOL_CODE))
            .andExpect(jsonPath("$.emailCurator").value(DEFAULT_EMAIL_CURATOR));
    }
    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .firstNm(UPDATED_FIRST_NM)
            .lastNm(UPDATED_LAST_NM)
            .middleNm(UPDATED_MIDDLE_NM)
            .email(UPDATED_EMAIL)
            .phoneNum(UPDATED_PHONE_NUM)
            .workType(UPDATED_WORK_TYPE)
            .birthDt(UPDATED_BIRTH_DT)
            .idTitle(UPDATED_ID_TITLE)
            .resourcePoolCode(UPDATED_RESOURCE_POOL_CODE)
            .emailCurator(UPDATED_EMAIL_CURATOR);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployee)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getFirstNm()).isEqualTo(UPDATED_FIRST_NM);
        assertThat(testEmployee.getLastNm()).isEqualTo(UPDATED_LAST_NM);
        assertThat(testEmployee.getMiddleNm()).isEqualTo(UPDATED_MIDDLE_NM);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
        assertThat(testEmployee.getWorkType()).isEqualTo(UPDATED_WORK_TYPE);
        assertThat(testEmployee.getBirthDt()).isEqualTo(UPDATED_BIRTH_DT);
        assertThat(testEmployee.getIdTitle()).isEqualTo(UPDATED_ID_TITLE);
        assertThat(testEmployee.getResourcePoolCode()).isEqualTo(UPDATED_RESOURCE_POOL_CODE);
        assertThat(testEmployee.getEmailCurator()).isEqualTo(UPDATED_EMAIL_CURATOR);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employee)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
