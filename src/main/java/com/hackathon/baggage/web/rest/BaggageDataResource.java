package com.hackathon.baggage.web.rest;

import com.hackathon.baggage.domain.BaggageData;
import com.hackathon.baggage.repository.BaggageDataRepository;
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
 * REST controller for managing {@link com.hackathon.baggage.domain.BaggageData}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BaggageDataResource {

    private final Logger log = LoggerFactory.getLogger(BaggageDataResource.class);

    private static final String ENTITY_NAME = "baggageData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BaggageDataRepository baggageDataRepository;

    public BaggageDataResource(BaggageDataRepository baggageDataRepository) {
        this.baggageDataRepository = baggageDataRepository;
    }

    /**
     * {@code POST  /baggage-data} : Create a new baggageData.
     *
     * @param baggageData the baggageData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new baggageData, or with status {@code 400 (Bad Request)} if the baggageData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/baggage-data")
    public ResponseEntity<BaggageData> createBaggageData(@RequestBody BaggageData baggageData) throws URISyntaxException {
        log.debug("REST request to save BaggageData : {}", baggageData);
        if (baggageData.getId() != null) {
            throw new BadRequestAlertException("A new baggageData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BaggageData result = baggageDataRepository.save(baggageData);
        return ResponseEntity.created(new URI("/api/baggage-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /baggage-data} : Updates an existing baggageData.
     *
     * @param baggageData the baggageData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated baggageData,
     * or with status {@code 400 (Bad Request)} if the baggageData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the baggageData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/baggage-data")
    public ResponseEntity<BaggageData> updateBaggageData(@RequestBody BaggageData baggageData) throws URISyntaxException {
        log.debug("REST request to update BaggageData : {}", baggageData);
        if (baggageData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BaggageData result = baggageDataRepository.save(baggageData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, baggageData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /baggage-data} : get all the baggageData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of baggageData in body.
     */
    @GetMapping("/baggage-data")
    public List<BaggageData> getAllBaggageData() {
        log.debug("REST request to get all BaggageData");
        return baggageDataRepository.findAll();
    }

    /**
     * {@code GET  /baggage-data/:id} : get the "id" baggageData.
     *
     * @param id the id of the baggageData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the baggageData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/baggage-data/{id}")
    public ResponseEntity<BaggageData> getBaggageData(@PathVariable Long id) {
        log.debug("REST request to get BaggageData : {}", id);
        Optional<BaggageData> baggageData = baggageDataRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(baggageData);
    }

    /**
     * {@code DELETE  /baggage-data/:id} : delete the "id" baggageData.
     *
     * @param id the id of the baggageData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/baggage-data/{id}")
    public ResponseEntity<Void> deleteBaggageData(@PathVariable Long id) {
        log.debug("REST request to delete BaggageData : {}", id);
        baggageDataRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
