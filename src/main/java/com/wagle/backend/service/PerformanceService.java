package com.wagle.backend.service;

import com.wagle.backend.dto.PerformanceCreateRequest;
import com.wagle.backend.dto.PerformanceUpdateRequest;
import com.wagle.backend.dto.PerformanceDeleteRequest;
import com.wagle.backend.entity.Performance;
import com.wagle.backend.repository.PerformanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    public PerformanceService(PerformanceRepository performanceRepository) {
        this.performanceRepository = performanceRepository;
    }

    public Performance createPerformance(PerformanceCreateRequest request) {
        Performance performance = new Performance();
        performance.setFestivalId(request.getFestivalId());
        performance.setUserId(request.getUserId());
        performance.setLocationId(request.getLocationId());
        performance.setPerformer(request.getPerformer());
        performance.setDescription(request.getDescription());
        performance.setStartTime(request.getStartTime());
        performance.setEndTime(request.getEndTime());
        performance.setPassword(request.getPassword());

        return performanceRepository.save(performance);
    }

    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    public Optional<Performance> getPerformance(Long performanceId) {
        return performanceRepository.findById(performanceId);
    }

    public Performance updatePerformance(Long performanceId, PerformanceUpdateRequest request, Long loginUserId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new RuntimeException("공연을 찾을 수 없습니다."));

        if (!performance.getUserId().equals(loginUserId)) {
            throw new RuntimeException("본인이 등록한 공연만 수정할 수 있습니다.");
        }

        if (!performance.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        if (request.getPerformer() != null && !request.getPerformer().isBlank()) {
            performance.setPerformer(request.getPerformer());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            performance.setDescription(request.getDescription());
        }
        if (request.getStartTime() != null) {
            performance.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            performance.setEndTime(request.getEndTime());
        }

        return performanceRepository.save(performance);
    }

    public void deletePerformance(Long performanceId, PerformanceDeleteRequest request, Long loginUserId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new RuntimeException("공연을 찾을 수 없습니다."));

        if (!performance.getUserId().equals(loginUserId)) {
            throw new RuntimeException("본인이 등록한 공연만 삭제할 수 있습니다.");
        }

        if (!performance.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        performanceRepository.delete(performance);
    }
}