package com.wagle.backend.repository;

import com.wagle.backend.entity.BoothReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoothReviewRepository extends JpaRepository<BoothReview, Long> {
    List<BoothReview> findByBoothId(Long boothId);
}