package com.hackathon.baggage.web.rest;

import com.hackathon.baggage.domain.Segment;
import com.hackathon.baggage.repository.SegmentRepository;
import com.hackathon.baggage.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hackathon.baggage.domain.Segment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SegmentResource {

    private final Logger log = LoggerFactory.getLogger(SegmentResource.class);

    private static final String ENTITY_NAME = "segment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SegmentRepository segmentRepository;

    public SegmentResource(SegmentRepository segmentRepository) {
        this.segmentRepository = segmentRepository;
    }

    /**
     * {@code POST  /segments} : Create a new segment.
     *
     * @param segment the segment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new segment, or with status {@code 400 (Bad Request)} if the segment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/segments")
    public ResponseEntity<Segment> createSegment(@RequestBody Segment segment) throws URISyntaxException {
        log.debug("REST request to save Segment : {}", segment);
        if (segment.getId() != null) {
            throw new BadRequestAlertException("A new segment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Segment result = segmentRepository.save(segment);
        return ResponseEntity.created(new URI("/api/segments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /segments} : Updates an existing segment.
     *
     * @param segment the segment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated segment,
     * or with status {@code 400 (Bad Request)} if the segment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the segment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/segments")
    public ResponseEntity<Segment> updateSegment(@RequestBody Segment segment) throws URISyntaxException {
        log.debug("REST request to update Segment : {}", segment);
        if (segment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Segment result = segmentRepository.save(segment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, segment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /segments} : get all the segments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of segments in body.
     */
    @GetMapping("/segments")
    public List<Segment> getAllSegments() {
        log.debug("REST request to get all Segments");
        return segmentRepository.findAll();
    }

    /**
     * {@code GET  /segments/:id} : get the "id" segment.
     *
     * @param id the id of the segment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the segment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/segments/{id}")
    public ResponseEntity<Segment> getSegment(@PathVariable Long id) {
        log.debug("REST request to get Segment : {}", id);
        Optional<Segment> segment = segmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(segment);
    }

    /**
     * {@code DELETE  /segments/:id} : delete the "id" segment.
     *
     * @param id the id of the segment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/segments/{id}")
    public ResponseEntity<Void> deleteSegment(@PathVariable Long id) {
        log.debug("REST request to delete Segment : {}", id);
        segmentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
