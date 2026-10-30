package com.wagle.wagleserver.controller;

import com.wagle.wagleserver.dto.BoothCreateRequest;
import com.wagle.wagleserver.dto.BoothUpdateRequest;
import com.wagle.wagleserver.dto.BoothDeleteRequest;
import com.wagle.wagleserver.entity.Booth;
import com.wagle.wagleserver.service.BoothService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booths")
public class BoothController {

    private final BoothService boothService;

    public BoothController(BoothService boothService) {
        this.boothService = boothService;
    }

    @PostMapping
    public ResponseEntity<Booth> createBooth(@RequestBody BoothCreateRequest request) {
        Booth booth = boothService.createBooth(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(booth);
    }

    @GetMapping
    public ResponseEntity<List<Booth>> getAllBooths() {
        List<Booth> booths = boothService.getAllBooths();
        return ResponseEntity.ok(booths);
    }

    @GetMapping("/{boothId}")
    public ResponseEntity<Booth> getBooth(@PathVariable Long boothId) {
        Booth booth = boothService.getBooth(boothId)
                .orElseThrow(() -> new RuntimeException("부스를 찾을 수 없습니다."));
        return ResponseEntity.ok(booth);
    }

    @PatchMapping("/{boothId}")
public ResponseEntity<Booth> updateBooth(@PathVariable Long boothId,
                                        @RequestBody BoothUpdateRequest request,
                                        HttpSession session) {
    Long loginUserId = (Long) session.getAttribute("userId");
    if (loginUserId == null) {
        throw new RuntimeException("로그인이 필요합니다.");
    }
    Booth booth = boothService.updateBooth(boothId, request, loginUserId);
    return ResponseEntity.ok(booth);
}

@DeleteMapping("/{boothId}")
public ResponseEntity<String> deleteBooth(@PathVariable Long boothId,
                                        @RequestBody BoothDeleteRequest request,
                                        HttpSession session) {
    Long loginUserId = (Long) session.getAttribute("userId");
    if (loginUserId == null) {
        throw new RuntimeException("로그인이 필요합니다.");
    }
    boothService.deleteBooth(boothId, request, loginUserId);
    return ResponseEntity.ok("부스가 삭제되었습니다.");
}

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}