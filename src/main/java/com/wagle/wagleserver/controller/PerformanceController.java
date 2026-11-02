package com.wagle.wagleserver.controller;

import com.wagle.wagleserver.dto.PerformanceCreateRequest;
import com.wagle.wagleserver.dto.PerformanceUpdateRequest;
import com.wagle.wagleserver.dto.PerformanceDeleteRequest;
import com.wagle.wagleserver.entity.Performance;
import com.wagle.wagleserver.service.PerformanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfor")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @PostMapping
    public ResponseEntity<Performance> createPerformance(@RequestBody PerformanceCreateRequest request) {
        Performance performance = performanceService.createPerformance(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(performance);
    }

    @GetMapping
    public ResponseEntity<List<Performance>> getAllPerformances() {
        return ResponseEntity.ok(performanceService.getAllPerformances());
    }

    @GetMapping("/{performanceId}")
    public ResponseEntity<Performance> getPerformance(@PathVariable Long performanceId) {
        Performance performance = performanceService.getPerformance(performanceId)
                .orElseThrow(() -> new RuntimeException("공연을 찾을 수 없습니다."));
        return ResponseEntity.ok(performance);
    }

    @PatchMapping("/{performanceId}")
    public ResponseEntity<Performance> updatePerformance(@PathVariable Long performanceId,
                                                           @RequestBody PerformanceUpdateRequest request,
                                                           HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        Performance performance = performanceService.updatePerformance(performanceId, request, loginUserId);
        return ResponseEntity.ok(performance);
    }

    @DeleteMapping("/{performanceId}")
    public ResponseEntity<String> deletePerformance(@PathVariable Long performanceId,
                                                    @RequestBody PerformanceDeleteRequest request,
                                                    HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        performanceService.deletePerformance(performanceId, request, loginUserId);
        return ResponseEntity.ok("공연이 삭제되었습니다.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}