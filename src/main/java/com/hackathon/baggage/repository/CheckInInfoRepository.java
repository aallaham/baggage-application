package com.hackathon.baggage.repository;

import com.hackathon.baggage.domain.CheckInInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CheckInInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckInInfoRepository extends JpaRepository<CheckInInfo, Long> {
}
