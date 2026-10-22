package com.wagle.backend.service;

import com.wagle.backend.dto.FestivalCreateRequest;
import com.wagle.backend.dto.FestivalUpdateRequest;
import com.wagle.backend.dto.FestivalDeleteRequest;
import com.wagle.backend.entity.Festival;
import com.wagle.backend.repository.FestivalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public FestivalService(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }

    public Festival createFestival(FestivalCreateRequest request, Long loginUserId) {
        Festival festival = new Festival();
        festival.setUserId(loginUserId);
        festival.setName(request.getName());
        festival.setYear(request.getYear());
        festival.setStartDate(request.getStartDate());
        festival.setEndDate(request.getEndDate());
        festival.setDescription(request.getDescription());
        festival.setPassword(request.getPassword());

        return festivalRepository.save(festival);
    }

    public List<Festival> getAllFestivals() {
        return festivalRepository.findAll();
    }

    public Optional<Festival> getFestival(Long festivalId) {
        return festivalRepository.findById(festivalId);
    }

    public Festival updateFestival(Long festivalId, FestivalUpdateRequest request, Long loginUserId) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(() -> new RuntimeException("축제를 찾을 수 없습니다."));

        if (!festival.getUserId().equals(loginUserId)) {
            throw new RuntimeException("본인이 등록한 축제만 수정할 수 있습니다.");
        }

        if (!festival.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        if (request.getName() != null && !request.getName().isBlank()) {
            festival.setName(request.getName());
        }
        if (request.getYear() != null) {
            festival.setYear(request.getYear());
        }
        if (request.getStartDate() != null) {
            festival.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            festival.setEndDate(request.getEndDate());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            festival.setDescription(request.getDescription());
        }

        return festivalRepository.save(festival);
    }

    public void deleteFestival(Long festivalId, FestivalDeleteRequest request, Long loginUserId) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(() -> new RuntimeException("축제를 찾을 수 없습니다."));

        if (!festival.getUserId().equals(loginUserId)) {
            throw new RuntimeException("본인이 등록한 축제만 삭제할 수 있습니다.");
        }

        if (!festival.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        festivalRepository.delete(festival);
    }
}