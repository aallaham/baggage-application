package com.hackathon.baggage.web.rest;

import com.hackathon.baggage.domain.CheckInInfo;
import com.hackathon.baggage.repository.CheckInInfoRepository;
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
 * REST controller for managing {@link com.hackathon.baggage.domain.CheckInInfo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CheckInInfoResource {

    private final Logger log = LoggerFactory.getLogger(CheckInInfoResource.class);

    private static final String ENTITY_NAME = "checkInInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckInInfoRepository checkInInfoRepository;

    public CheckInInfoResource(CheckInInfoRepository checkInInfoRepository) {
        this.checkInInfoRepository = checkInInfoRepository;
    }

    /**
     * {@code POST  /check-in-infos} : Create a new checkInInfo.
     *
     * @param checkInInfo the checkInInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkInInfo, or with status {@code 400 (Bad Request)} if the checkInInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-in-infos")
    public ResponseEntity<CheckInInfo> createCheckInInfo(@RequestBody CheckInInfo checkInInfo) throws URISyntaxException {
        log.debug("REST request to save CheckInInfo : {}", checkInInfo);
        if (checkInInfo.getId() != null) {
            throw new BadRequestAlertException("A new checkInInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckInInfo result = checkInInfoRepository.save(checkInInfo);
        return ResponseEntity.created(new URI("/api/check-in-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /check-in-infos} : Updates an existing checkInInfo.
     *
     * @param checkInInfo the checkInInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkInInfo,
     * or with status {@code 400 (Bad Request)} if the checkInInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkInInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-in-infos")
    public ResponseEntity<CheckInInfo> updateCheckInInfo(@RequestBody CheckInInfo checkInInfo) throws URISyntaxException {
        log.debug("REST request to update CheckInInfo : {}", checkInInfo);
        if (checkInInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckInInfo result = checkInInfoRepository.save(checkInInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkInInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /check-in-infos} : get all the checkInInfos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkInInfos in body.
     */
    @GetMapping("/check-in-infos")
    public List<CheckInInfo> getAllCheckInInfos() {
        log.debug("REST request to get all CheckInInfos");
        return checkInInfoRepository.findAll();
    }

    /**
     * {@code GET  /check-in-infos/:id} : get the "id" checkInInfo.
     *
     * @param id the id of the checkInInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkInInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-in-infos/{id}")
    public ResponseEntity<CheckInInfo> getCheckInInfo(@PathVariable Long id) {
        log.debug("REST request to get CheckInInfo : {}", id);
        Optional<CheckInInfo> checkInInfo = checkInInfoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(checkInInfo);
    }

    /**
     * {@code DELETE  /check-in-infos/:id} : delete the "id" checkInInfo.
     *
     * @param id the id of the checkInInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-in-infos/{id}")
    public ResponseEntity<Void> deleteCheckInInfo(@PathVariable Long id) {
        log.debug("REST request to delete CheckInInfo : {}", id);
        checkInInfoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
