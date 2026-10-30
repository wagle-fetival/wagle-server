package com.wagle.wagleserver.service;

import com.wagle.wagleserver.dto.BoothReviewCreateRequest;
import com.wagle.wagleserver.entity.BoothReview;
import com.wagle.wagleserver.repository.BoothReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoothReviewService {

    private final BoothReviewRepository boothReviewRepository;

    public BoothReviewService(BoothReviewRepository boothReviewRepository) {
        this.boothReviewRepository = boothReviewRepository;
    }

    public BoothReview createReview(Long boothId, BoothReviewCreateRequest request, Long loginUserId) {
        BoothReview review = new BoothReview();
        review.setBoothId(boothId);
        review.setUserId(loginUserId);
        review.setContent(request.getContent());

        return boothReviewRepository.save(review);
    }

    public List<BoothReview> getReviewsByBooth(Long boothId) {
        return boothReviewRepository.findByBoothId(boothId);
    }
}