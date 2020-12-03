package com.hackathon.baggage.web.rest;

import com.hackathon.baggage.BaggageHandlerApp;
import com.hackathon.baggage.domain.Segment;
import com.hackathon.baggage.repository.SegmentRepository;

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
 * Integration tests for the {@link SegmentResource} REST controller.
 */
@SpringBootTest(classes = BaggageHandlerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SegmentResourceIT {

    private static final String DEFAULT_SEAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_DEPARTURE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEPARTURE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SegmentRepository segmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSegmentMockMvc;

    private Segment segment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segment createEntity(EntityManager em) {
        Segment segment = new Segment()
            .seatNumber(DEFAULT_SEAT_NUMBER)
            .departureDate(DEFAULT_DEPARTURE_DATE);
        return segment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Segment createUpdatedEntity(EntityManager em) {
        Segment segment = new Segment()
            .seatNumber(UPDATED_SEAT_NUMBER)
            .departureDate(UPDATED_DEPARTURE_DATE);
        return segment;
    }

    @BeforeEach
    public void initTest() {
        segment = createEntity(em);
    }

    @Test
    @Transactional
    public void createSegment() throws Exception {
        int databaseSizeBeforeCreate = segmentRepository.findAll().size();
        // Create the Segment
        restSegmentMockMvc.perform(post("/api/segments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(segment)))
            .andExpect(status().isCreated());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeCreate + 1);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getSeatNumber()).isEqualTo(DEFAULT_SEAT_NUMBER);
        assertThat(testSegment.getDepartureDate()).isEqualTo(DEFAULT_DEPARTURE_DATE);
    }

    @Test
    @Transactional
    public void createSegmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = segmentRepository.findAll().size();

        // Create the Segment with an existing ID
        segment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSegmentMockMvc.perform(post("/api/segments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(segment)))
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSegments() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        // Get all the segmentList
        restSegmentMockMvc.perform(get("/api/segments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(segment.getId().intValue())))
            .andExpect(jsonPath("$.[*].seatNumber").value(hasItem(DEFAULT_SEAT_NUMBER)))
            .andExpect(jsonPath("$.[*].departureDate").value(hasItem(DEFAULT_DEPARTURE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        // Get the segment
        restSegmentMockMvc.perform(get("/api/segments/{id}", segment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(segment.getId().intValue()))
            .andExpect(jsonPath("$.seatNumber").value(DEFAULT_SEAT_NUMBER))
            .andExpect(jsonPath("$.departureDate").value(DEFAULT_DEPARTURE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSegment() throws Exception {
        // Get the segment
        restSegmentMockMvc.perform(get("/api/segments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();

        // Update the segment
        Segment updatedSegment = segmentRepository.findById(segment.getId()).get();
        // Disconnect from session so that the updates on updatedSegment are not directly saved in db
        em.detach(updatedSegment);
        updatedSegment
            .seatNumber(UPDATED_SEAT_NUMBER)
            .departureDate(UPDATED_DEPARTURE_DATE);

        restSegmentMockMvc.perform(put("/api/segments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSegment)))
            .andExpect(status().isOk());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
        Segment testSegment = segmentList.get(segmentList.size() - 1);
        assertThat(testSegment.getSeatNumber()).isEqualTo(UPDATED_SEAT_NUMBER);
        assertThat(testSegment.getDepartureDate()).isEqualTo(UPDATED_DEPARTURE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSegment() throws Exception {
        int databaseSizeBeforeUpdate = segmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSegmentMockMvc.perform(put("/api/segments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(segment)))
            .andExpect(status().isBadRequest());

        // Validate the Segment in the database
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSegment() throws Exception {
        // Initialize the database
        segmentRepository.saveAndFlush(segment);

        int databaseSizeBeforeDelete = segmentRepository.findAll().size();

        // Delete the segment
        restSegmentMockMvc.perform(delete("/api/segments/{id}", segment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Segment> segmentList = segmentRepository.findAll();
        assertThat(segmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
