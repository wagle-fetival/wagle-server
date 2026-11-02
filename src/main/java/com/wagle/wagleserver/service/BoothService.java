package com.wagle.wagleserver.service;

import com.wagle.wagleserver.dto.BoothCreateRequest;
import com.wagle.wagleserver.dto.BoothUpdateRequest;
import com.wagle.wagleserver.dto.BoothDeleteRequest;
import com.wagle.wagleserver.entity.Booth;
import com.wagle.wagleserver.repository.BoothRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoothService {

    private final BoothRepository boothRepository;

    public BoothService(BoothRepository boothRepository) {
        this.boothRepository = boothRepository;
    }

    public Booth createBooth(BoothCreateRequest request) {
        Booth booth = new Booth();
        booth.setFestivalId(request.getFestivalId());
        booth.setUserId(request.getUserId());
        booth.setCategoryId(request.getCategoryId());
        booth.setLocationId(request.getLocationId());
        booth.setName(request.getName());
        booth.setDescription(request.getDescription());
        booth.setPassword(request.getPassword());
        booth.setIsDeleted(false);

        return boothRepository.save(booth);
    }

    public List<Booth> getAllBooths() {
        return boothRepository.findAll()
                .stream()
                .filter(booth -> !booth.getIsDeleted())
                .toList();
    }

    public Optional<Booth> getBooth(Long boothId) {
        return boothRepository.findById(boothId);
    }

   public Booth updateBooth(Long boothId, BoothUpdateRequest request, Long loginUserId) {
    Booth booth = boothRepository.findById(boothId)
            .orElseThrow(() -> new RuntimeException("부스를 찾을 수 없습니다."));

    if (!booth.getUserId().equals(loginUserId)) {
        throw new RuntimeException("본인이 등록한 부스만 수정할 수 있습니다.");
    }

    if (!booth.getPassword().equals(request.getPassword())) {
        throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    if (request.getName() != null && !request.getName().isBlank()) {
        booth.setName(request.getName());
    }

    if (request.getDescription() != null && !request.getDescription().isBlank()) {
        booth.setDescription(request.getDescription());
    }

    return boothRepository.save(booth);
}

public void deleteBooth(Long boothId, BoothDeleteRequest request, Long loginUserId) {
    Booth booth = boothRepository.findById(boothId)
            .orElseThrow(() -> new RuntimeException("부스를 찾을 수 없습니다."));

    if (!booth.getUserId().equals(loginUserId)) {
        throw new RuntimeException("본인이 등록한 부스만 삭제할 수 있습니다.");
    }

    if (booth.getIsDeleted()) {
        throw new RuntimeException("이미 삭제된 부스입니다.");
    }

    if (!booth.getPassword().equals(request.getPassword())) {
        throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    booth.setIsDeleted(true);
    boothRepository.save(booth);
}
}