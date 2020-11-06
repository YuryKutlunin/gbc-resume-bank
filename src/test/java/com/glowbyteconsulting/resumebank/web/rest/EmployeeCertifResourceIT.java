package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.EmployeeCertif;
import com.glowbyteconsulting.resumebank.repository.EmployeeCertifRepository;

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
 * Integration tests for the {@link EmployeeCertifResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeCertifResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_CERTIFICATE = 1L;
    private static final Long UPDATED_ID_CERTIFICATE = 2L;

    private static final Instant DEFAULT_START_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmployeeCertifRepository employeeCertifRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeCertifMockMvc;

    private EmployeeCertif employeeCertif;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeCertif createEntity(EntityManager em) {
        EmployeeCertif employeeCertif = new EmployeeCertif()
            .email(DEFAULT_EMAIL)
            .idCertificate(DEFAULT_ID_CERTIFICATE)
            .startDt(DEFAULT_START_DT)
            .endDt(DEFAULT_END_DT);
        return employeeCertif;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeCertif createUpdatedEntity(EntityManager em) {
        EmployeeCertif employeeCertif = new EmployeeCertif()
            .email(UPDATED_EMAIL)
            .idCertificate(UPDATED_ID_CERTIFICATE)
            .startDt(UPDATED_START_DT)
            .endDt(UPDATED_END_DT);
        return employeeCertif;
    }

    @BeforeEach
    public void initTest() {
        employeeCertif = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeCertif() throws Exception {
        int databaseSizeBeforeCreate = employeeCertifRepository.findAll().size();
        // Create the EmployeeCertif
        restEmployeeCertifMockMvc.perform(post("/api/employee-certifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeCertif)))
            .andExpect(status().isCreated());

        // Validate the EmployeeCertif in the database
        List<EmployeeCertif> employeeCertifList = employeeCertifRepository.findAll();
        assertThat(employeeCertifList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeCertif testEmployeeCertif = employeeCertifList.get(employeeCertifList.size() - 1);
        assertThat(testEmployeeCertif.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployeeCertif.getIdCertificate()).isEqualTo(DEFAULT_ID_CERTIFICATE);
        assertThat(testEmployeeCertif.getStartDt()).isEqualTo(DEFAULT_START_DT);
        assertThat(testEmployeeCertif.getEndDt()).isEqualTo(DEFAULT_END_DT);
    }

    @Test
    @Transactional
    public void createEmployeeCertifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeCertifRepository.findAll().size();

        // Create the EmployeeCertif with an existing ID
        employeeCertif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeCertifMockMvc.perform(post("/api/employee-certifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeCertif)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCertif in the database
        List<EmployeeCertif> employeeCertifList = employeeCertifRepository.findAll();
        assertThat(employeeCertifList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeCertifs() throws Exception {
        // Initialize the database
        employeeCertifRepository.saveAndFlush(employeeCertif);

        // Get all the employeeCertifList
        restEmployeeCertifMockMvc.perform(get("/api/employee-certifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeCertif.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].idCertificate").value(hasItem(DEFAULT_ID_CERTIFICATE.intValue())))
            .andExpect(jsonPath("$.[*].startDt").value(hasItem(DEFAULT_START_DT.toString())))
            .andExpect(jsonPath("$.[*].endDt").value(hasItem(DEFAULT_END_DT.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeeCertif() throws Exception {
        // Initialize the database
        employeeCertifRepository.saveAndFlush(employeeCertif);

        // Get the employeeCertif
        restEmployeeCertifMockMvc.perform(get("/api/employee-certifs/{id}", employeeCertif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeCertif.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.idCertificate").value(DEFAULT_ID_CERTIFICATE.intValue()))
            .andExpect(jsonPath("$.startDt").value(DEFAULT_START_DT.toString()))
            .andExpect(jsonPath("$.endDt").value(DEFAULT_END_DT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmployeeCertif() throws Exception {
        // Get the employeeCertif
        restEmployeeCertifMockMvc.perform(get("/api/employee-certifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeCertif() throws Exception {
        // Initialize the database
        employeeCertifRepository.saveAndFlush(employeeCertif);

        int databaseSizeBeforeUpdate = employeeCertifRepository.findAll().size();

        // Update the employeeCertif
        EmployeeCertif updatedEmployeeCertif = employeeCertifRepository.findById(employeeCertif.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeCertif are not directly saved in db
        em.detach(updatedEmployeeCertif);
        updatedEmployeeCertif
            .email(UPDATED_EMAIL)
            .idCertificate(UPDATED_ID_CERTIFICATE)
            .startDt(UPDATED_START_DT)
            .endDt(UPDATED_END_DT);

        restEmployeeCertifMockMvc.perform(put("/api/employee-certifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeCertif)))
            .andExpect(status().isOk());

        // Validate the EmployeeCertif in the database
        List<EmployeeCertif> employeeCertifList = employeeCertifRepository.findAll();
        assertThat(employeeCertifList).hasSize(databaseSizeBeforeUpdate);
        EmployeeCertif testEmployeeCertif = employeeCertifList.get(employeeCertifList.size() - 1);
        assertThat(testEmployeeCertif.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployeeCertif.getIdCertificate()).isEqualTo(UPDATED_ID_CERTIFICATE);
        assertThat(testEmployeeCertif.getStartDt()).isEqualTo(UPDATED_START_DT);
        assertThat(testEmployeeCertif.getEndDt()).isEqualTo(UPDATED_END_DT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeCertif() throws Exception {
        int databaseSizeBeforeUpdate = employeeCertifRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeCertifMockMvc.perform(put("/api/employee-certifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeCertif)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeCertif in the database
        List<EmployeeCertif> employeeCertifList = employeeCertifRepository.findAll();
        assertThat(employeeCertifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeCertif() throws Exception {
        // Initialize the database
        employeeCertifRepository.saveAndFlush(employeeCertif);

        int databaseSizeBeforeDelete = employeeCertifRepository.findAll().size();

        // Delete the employeeCertif
        restEmployeeCertifMockMvc.perform(delete("/api/employee-certifs/{id}", employeeCertif.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeCertif> employeeCertifList = employeeCertifRepository.findAll();
        assertThat(employeeCertifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
