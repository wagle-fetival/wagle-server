package com.wagle.wagleserver.repository;

import com.wagle.wagleserver.entity.BoothReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoothReviewRepository extends JpaRepository<BoothReview, Long> {
    List<BoothReview> findByBoothId(Long boothId);
}