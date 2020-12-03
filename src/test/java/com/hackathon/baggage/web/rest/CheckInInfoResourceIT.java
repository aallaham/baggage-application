package com.hackathon.baggage.web.rest;

import com.hackathon.baggage.BaggageHandlerApp;
import com.hackathon.baggage.domain.CheckInInfo;
import com.hackathon.baggage.repository.CheckInInfoRepository;

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
 * Integration tests for the {@link CheckInInfoResource} REST controller.
 */
@SpringBootTest(classes = BaggageHandlerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CheckInInfoResourceIT {

    private static final String DEFAULT_MEMBER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PNR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PNR_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_IN_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_IN_STATUS = "BBBBBBBBBB";

    @Autowired
    private CheckInInfoRepository checkInInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckInInfoMockMvc;

    private CheckInInfo checkInInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckInInfo createEntity(EntityManager em) {
        CheckInInfo checkInInfo = new CheckInInfo()
            .memberName(DEFAULT_MEMBER_NAME)
            .pnrNumber(DEFAULT_PNR_NUMBER)
            .checkInStatus(DEFAULT_CHECK_IN_STATUS);
        return checkInInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckInInfo createUpdatedEntity(EntityManager em) {
        CheckInInfo checkInInfo = new CheckInInfo()
            .memberName(UPDATED_MEMBER_NAME)
            .pnrNumber(UPDATED_PNR_NUMBER)
            .checkInStatus(UPDATED_CHECK_IN_STATUS);
        return checkInInfo;
    }

    @BeforeEach
    public void initTest() {
        checkInInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckInInfo() throws Exception {
        int databaseSizeBeforeCreate = checkInInfoRepository.findAll().size();
        // Create the CheckInInfo
        restCheckInInfoMockMvc.perform(post("/api/check-in-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkInInfo)))
            .andExpect(status().isCreated());

        // Validate the CheckInInfo in the database
        List<CheckInInfo> checkInInfoList = checkInInfoRepository.findAll();
        assertThat(checkInInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CheckInInfo testCheckInInfo = checkInInfoList.get(checkInInfoList.size() - 1);
        assertThat(testCheckInInfo.getMemberName()).isEqualTo(DEFAULT_MEMBER_NAME);
        assertThat(testCheckInInfo.getPnrNumber()).isEqualTo(DEFAULT_PNR_NUMBER);
        assertThat(testCheckInInfo.getCheckInStatus()).isEqualTo(DEFAULT_CHECK_IN_STATUS);
    }

    @Test
    @Transactional
    public void createCheckInInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkInInfoRepository.findAll().size();

        // Create the CheckInInfo with an existing ID
        checkInInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckInInfoMockMvc.perform(post("/api/check-in-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkInInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CheckInInfo in the database
        List<CheckInInfo> checkInInfoList = checkInInfoRepository.findAll();
        assertThat(checkInInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCheckInInfos() throws Exception {
        // Initialize the database
        checkInInfoRepository.saveAndFlush(checkInInfo);

        // Get all the checkInInfoList
        restCheckInInfoMockMvc.perform(get("/api/check-in-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkInInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].memberName").value(hasItem(DEFAULT_MEMBER_NAME)))
            .andExpect(jsonPath("$.[*].pnrNumber").value(hasItem(DEFAULT_PNR_NUMBER)))
            .andExpect(jsonPath("$.[*].checkInStatus").value(hasItem(DEFAULT_CHECK_IN_STATUS)));
    }
    
    @Test
    @Transactional
    public void getCheckInInfo() throws Exception {
        // Initialize the database
        checkInInfoRepository.saveAndFlush(checkInInfo);

        // Get the checkInInfo
        restCheckInInfoMockMvc.perform(get("/api/check-in-infos/{id}", checkInInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkInInfo.getId().intValue()))
            .andExpect(jsonPath("$.memberName").value(DEFAULT_MEMBER_NAME))
            .andExpect(jsonPath("$.pnrNumber").value(DEFAULT_PNR_NUMBER))
            .andExpect(jsonPath("$.checkInStatus").value(DEFAULT_CHECK_IN_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingCheckInInfo() throws Exception {
        // Get the checkInInfo
        restCheckInInfoMockMvc.perform(get("/api/check-in-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckInInfo() throws Exception {
        // Initialize the database
        checkInInfoRepository.saveAndFlush(checkInInfo);

        int databaseSizeBeforeUpdate = checkInInfoRepository.findAll().size();

        // Update the checkInInfo
        CheckInInfo updatedCheckInInfo = checkInInfoRepository.findById(checkInInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCheckInInfo are not directly saved in db
        em.detach(updatedCheckInInfo);
        updatedCheckInInfo
            .memberName(UPDATED_MEMBER_NAME)
            .pnrNumber(UPDATED_PNR_NUMBER)
            .checkInStatus(UPDATED_CHECK_IN_STATUS);

        restCheckInInfoMockMvc.perform(put("/api/check-in-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCheckInInfo)))
            .andExpect(status().isOk());

        // Validate the CheckInInfo in the database
        List<CheckInInfo> checkInInfoList = checkInInfoRepository.findAll();
        assertThat(checkInInfoList).hasSize(databaseSizeBeforeUpdate);
        CheckInInfo testCheckInInfo = checkInInfoList.get(checkInInfoList.size() - 1);
        assertThat(testCheckInInfo.getMemberName()).isEqualTo(UPDATED_MEMBER_NAME);
        assertThat(testCheckInInfo.getPnrNumber()).isEqualTo(UPDATED_PNR_NUMBER);
        assertThat(testCheckInInfo.getCheckInStatus()).isEqualTo(UPDATED_CHECK_IN_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckInInfo() throws Exception {
        int databaseSizeBeforeUpdate = checkInInfoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckInInfoMockMvc.perform(put("/api/check-in-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkInInfo)))
            .andExpect(status().isBadRequest());

        // Validate the CheckInInfo in the database
        List<CheckInInfo> checkInInfoList = checkInInfoRepository.findAll();
        assertThat(checkInInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckInInfo() throws Exception {
        // Initialize the database
        checkInInfoRepository.saveAndFlush(checkInInfo);

        int databaseSizeBeforeDelete = checkInInfoRepository.findAll().size();

        // Delete the checkInInfo
        restCheckInInfoMockMvc.perform(delete("/api/check-in-infos/{id}", checkInInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckInInfo> checkInInfoList = checkInInfoRepository.findAll();
        assertThat(checkInInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
