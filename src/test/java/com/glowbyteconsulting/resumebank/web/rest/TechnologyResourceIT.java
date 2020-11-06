package com.glowbyteconsulting.resumebank.web.rest;

import com.glowbyteconsulting.resumebank.GbcResumeBankApp;
import com.glowbyteconsulting.resumebank.domain.Technology;
import com.glowbyteconsulting.resumebank.repository.TechnologyRepository;

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
 * Integration tests for the {@link TechnologyResource} REST controller.
 */
@SpringBootTest(classes = GbcResumeBankApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TechnologyResourceIT {

    private static final Long DEFAULT_ID_TECHNOLOGY = 1L;
    private static final Long UPDATED_ID_TECHNOLOGY = 2L;

    private static final String DEFAULT_TECHNOLOGY_NM = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGY_NM = "BBBBBBBBBB";

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTechnologyMockMvc;

    private Technology technology;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Technology createEntity(EntityManager em) {
        Technology technology = new Technology()
            .idTechnology(DEFAULT_ID_TECHNOLOGY)
            .technologyNm(DEFAULT_TECHNOLOGY_NM);
        return technology;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Technology createUpdatedEntity(EntityManager em) {
        Technology technology = new Technology()
            .idTechnology(UPDATED_ID_TECHNOLOGY)
            .technologyNm(UPDATED_TECHNOLOGY_NM);
        return technology;
    }

    @BeforeEach
    public void initTest() {
        technology = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechnology() throws Exception {
        int databaseSizeBeforeCreate = technologyRepository.findAll().size();
        // Create the Technology
        restTechnologyMockMvc.perform(post("/api/technologies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technology)))
            .andExpect(status().isCreated());

        // Validate the Technology in the database
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeCreate + 1);
        Technology testTechnology = technologyList.get(technologyList.size() - 1);
        assertThat(testTechnology.getIdTechnology()).isEqualTo(DEFAULT_ID_TECHNOLOGY);
        assertThat(testTechnology.getTechnologyNm()).isEqualTo(DEFAULT_TECHNOLOGY_NM);
    }

    @Test
    @Transactional
    public void createTechnologyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = technologyRepository.findAll().size();

        // Create the Technology with an existing ID
        technology.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechnologyMockMvc.perform(post("/api/technologies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technology)))
            .andExpect(status().isBadRequest());

        // Validate the Technology in the database
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTechnologies() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get all the technologyList
        restTechnologyMockMvc.perform(get("/api/technologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technology.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTechnology").value(hasItem(DEFAULT_ID_TECHNOLOGY.intValue())))
            .andExpect(jsonPath("$.[*].technologyNm").value(hasItem(DEFAULT_TECHNOLOGY_NM)));
    }
    
    @Test
    @Transactional
    public void getTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologies/{id}", technology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(technology.getId().intValue()))
            .andExpect(jsonPath("$.idTechnology").value(DEFAULT_ID_TECHNOLOGY.intValue()))
            .andExpect(jsonPath("$.technologyNm").value(DEFAULT_TECHNOLOGY_NM));
    }
    @Test
    @Transactional
    public void getNonExistingTechnology() throws Exception {
        // Get the technology
        restTechnologyMockMvc.perform(get("/api/technologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        int databaseSizeBeforeUpdate = technologyRepository.findAll().size();

        // Update the technology
        Technology updatedTechnology = technologyRepository.findById(technology.getId()).get();
        // Disconnect from session so that the updates on updatedTechnology are not directly saved in db
        em.detach(updatedTechnology);
        updatedTechnology
            .idTechnology(UPDATED_ID_TECHNOLOGY)
            .technologyNm(UPDATED_TECHNOLOGY_NM);

        restTechnologyMockMvc.perform(put("/api/technologies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTechnology)))
            .andExpect(status().isOk());

        // Validate the Technology in the database
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeUpdate);
        Technology testTechnology = technologyList.get(technologyList.size() - 1);
        assertThat(testTechnology.getIdTechnology()).isEqualTo(UPDATED_ID_TECHNOLOGY);
        assertThat(testTechnology.getTechnologyNm()).isEqualTo(UPDATED_TECHNOLOGY_NM);
    }

    @Test
    @Transactional
    public void updateNonExistingTechnology() throws Exception {
        int databaseSizeBeforeUpdate = technologyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnologyMockMvc.perform(put("/api/technologies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technology)))
            .andExpect(status().isBadRequest());

        // Validate the Technology in the database
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechnology() throws Exception {
        // Initialize the database
        technologyRepository.saveAndFlush(technology);

        int databaseSizeBeforeDelete = technologyRepository.findAll().size();

        // Delete the technology
        restTechnologyMockMvc.perform(delete("/api/technologies/{id}", technology.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Technology> technologyList = technologyRepository.findAll();
        assertThat(technologyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
