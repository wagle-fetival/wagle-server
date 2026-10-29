package com.wagle.backend.controller;

import com.wagle.backend.dto.BoothReviewCreateRequest;
import com.wagle.backend.entity.BoothReview;
import com.wagle.backend.service.BoothReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booths/{boothId}/reviews")
public class BoothReviewController {

    private final BoothReviewService boothReviewService;

    public BoothReviewController(BoothReviewService boothReviewService) {
        this.boothReviewService = boothReviewService;
    }

    @PostMapping
    public ResponseEntity<BoothReview> createReview(@PathVariable Long boothId,
                                                     @RequestBody BoothReviewCreateRequest request,
                                                     HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        BoothReview review = boothReviewService.createReview(boothId, request, loginUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @GetMapping
    public ResponseEntity<List<BoothReview>> getReviews(@PathVariable Long boothId) {
        return ResponseEntity.ok(boothReviewService.getReviewsByBooth(boothId));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}