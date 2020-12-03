package com.hackathon.baggage.repository;

import com.hackathon.baggage.domain.Segment;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Segment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
}
