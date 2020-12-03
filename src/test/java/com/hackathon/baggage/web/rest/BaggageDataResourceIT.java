package com.hackathon.baggage.web.rest;

import com.hackathon.baggage.BaggageHandlerApp;
import com.hackathon.baggage.domain.BaggageData;
import com.hackathon.baggage.repository.BaggageDataRepository;

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
 * Integration tests for the {@link BaggageDataResource} REST controller.
 */
@SpringBootTest(classes = BaggageHandlerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BaggageDataResourceIT {

    private static final String DEFAULT_BAGGAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_BAGGAGE_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_WEIGHT = 1L;
    private static final Long UPDATED_WEIGHT = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_QR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_QR_CODE = "BBBBBBBBBB";

    @Autowired
    private BaggageDataRepository baggageDataRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBaggageDataMockMvc;

    private BaggageData baggageData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BaggageData createEntity(EntityManager em) {
        BaggageData baggageData = new BaggageData()
            .baggageId(DEFAULT_BAGGAGE_ID)
            .weight(DEFAULT_WEIGHT)
            .status(DEFAULT_STATUS)
            .qrCode(DEFAULT_QR_CODE);
        return baggageData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BaggageData createUpdatedEntity(EntityManager em) {
        BaggageData baggageData = new BaggageData()
            .baggageId(UPDATED_BAGGAGE_ID)
            .weight(UPDATED_WEIGHT)
            .status(UPDATED_STATUS)
            .qrCode(UPDATED_QR_CODE);
        return baggageData;
    }

    @BeforeEach
    public void initTest() {
        baggageData = createEntity(em);
    }

    @Test
    @Transactional
    public void createBaggageData() throws Exception {
        int databaseSizeBeforeCreate = baggageDataRepository.findAll().size();
        // Create the BaggageData
        restBaggageDataMockMvc.perform(post("/api/baggage-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(baggageData)))
            .andExpect(status().isCreated());

        // Validate the BaggageData in the database
        List<BaggageData> baggageDataList = baggageDataRepository.findAll();
        assertThat(baggageDataList).hasSize(databaseSizeBeforeCreate + 1);
        BaggageData testBaggageData = baggageDataList.get(baggageDataList.size() - 1);
        assertThat(testBaggageData.getBaggageId()).isEqualTo(DEFAULT_BAGGAGE_ID);
        assertThat(testBaggageData.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testBaggageData.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBaggageData.getQrCode()).isEqualTo(DEFAULT_QR_CODE);
    }

    @Test
    @Transactional
    public void createBaggageDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = baggageDataRepository.findAll().size();

        // Create the BaggageData with an existing ID
        baggageData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaggageDataMockMvc.perform(post("/api/baggage-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(baggageData)))
            .andExpect(status().isBadRequest());

        // Validate the BaggageData in the database
        List<BaggageData> baggageDataList = baggageDataRepository.findAll();
        assertThat(baggageDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBaggageData() throws Exception {
        // Initialize the database
        baggageDataRepository.saveAndFlush(baggageData);

        // Get all the baggageDataList
        restBaggageDataMockMvc.perform(get("/api/baggage-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baggageData.getId().intValue())))
            .andExpect(jsonPath("$.[*].baggageId").value(hasItem(DEFAULT_BAGGAGE_ID)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].qrCode").value(hasItem(DEFAULT_QR_CODE)));
    }
    
    @Test
    @Transactional
    public void getBaggageData() throws Exception {
        // Initialize the database
        baggageDataRepository.saveAndFlush(baggageData);

        // Get the baggageData
        restBaggageDataMockMvc.perform(get("/api/baggage-data/{id}", baggageData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(baggageData.getId().intValue()))
            .andExpect(jsonPath("$.baggageId").value(DEFAULT_BAGGAGE_ID))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.qrCode").value(DEFAULT_QR_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingBaggageData() throws Exception {
        // Get the baggageData
        restBaggageDataMockMvc.perform(get("/api/baggage-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBaggageData() throws Exception {
        // Initialize the database
        baggageDataRepository.saveAndFlush(baggageData);

        int databaseSizeBeforeUpdate = baggageDataRepository.findAll().size();

        // Update the baggageData
        BaggageData updatedBaggageData = baggageDataRepository.findById(baggageData.getId()).get();
        // Disconnect from session so that the updates on updatedBaggageData are not directly saved in db
        em.detach(updatedBaggageData);
        updatedBaggageData
            .baggageId(UPDATED_BAGGAGE_ID)
            .weight(UPDATED_WEIGHT)
            .status(UPDATED_STATUS)
            .qrCode(UPDATED_QR_CODE);

        restBaggageDataMockMvc.perform(put("/api/baggage-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBaggageData)))
            .andExpect(status().isOk());

        // Validate the BaggageData in the database
        List<BaggageData> baggageDataList = baggageDataRepository.findAll();
        assertThat(baggageDataList).hasSize(databaseSizeBeforeUpdate);
        BaggageData testBaggageData = baggageDataList.get(baggageDataList.size() - 1);
        assertThat(testBaggageData.getBaggageId()).isEqualTo(UPDATED_BAGGAGE_ID);
        assertThat(testBaggageData.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testBaggageData.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBaggageData.getQrCode()).isEqualTo(UPDATED_QR_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingBaggageData() throws Exception {
        int databaseSizeBeforeUpdate = baggageDataRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaggageDataMockMvc.perform(put("/api/baggage-data")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(baggageData)))
            .andExpect(status().isBadRequest());

        // Validate the BaggageData in the database
        List<BaggageData> baggageDataList = baggageDataRepository.findAll();
        assertThat(baggageDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBaggageData() throws Exception {
        // Initialize the database
        baggageDataRepository.saveAndFlush(baggageData);

        int databaseSizeBeforeDelete = baggageDataRepository.findAll().size();

        // Delete the baggageData
        restBaggageDataMockMvc.perform(delete("/api/baggage-data/{id}", baggageData.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BaggageData> baggageDataList = baggageDataRepository.findAll();
        assertThat(baggageDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
