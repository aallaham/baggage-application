package com.hackathon.baggage.repository;

import com.hackathon.baggage.domain.BaggageData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BaggageData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaggageDataRepository extends JpaRepository<BaggageData, Long> {
}
