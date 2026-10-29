package com.wagle.backend.controller;

import com.wagle.backend.dto.FestivalCreateRequest;
import com.wagle.backend.dto.FestivalUpdateRequest;
import com.wagle.backend.dto.FestivalDeleteRequest;
import com.wagle.backend.entity.Festival;
import com.wagle.backend.service.FestivalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festival")
public class FestivalController {

    private final FestivalService festivalService;

    public FestivalController(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @PostMapping
    public ResponseEntity<Festival> createFestival(@RequestBody FestivalCreateRequest request,
                                                     HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        Festival festival = festivalService.createFestival(request, loginUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(festival);
    }

    @GetMapping
    public ResponseEntity<List<Festival>> getAllFestivals() {
        return ResponseEntity.ok(festivalService.getAllFestivals());
    }

    @GetMapping("/{festivalId}")
    public ResponseEntity<Festival> getFestival(@PathVariable Long festivalId) {
        Festival festival = festivalService.getFestival(festivalId)
                .orElseThrow(() -> new RuntimeException("축제를 찾을 수 없습니다."));
        return ResponseEntity.ok(festival);
    }

    @PatchMapping("/{festivalId}")
    public ResponseEntity<Festival> updateFestival(@PathVariable Long festivalId,
                                                     @RequestBody FestivalUpdateRequest request,
                                                     HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        Festival festival = festivalService.updateFestival(festivalId, request, loginUserId);
        return ResponseEntity.ok(festival);
    }

    @DeleteMapping("/{festivalId}")
    public ResponseEntity<String> deleteFestival(@PathVariable Long festivalId,
                                                   @RequestBody FestivalDeleteRequest request,
                                                   HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("userId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        festivalService.deleteFestival(festivalId, request, loginUserId);
        return ResponseEntity.ok("축제가 삭제되었습니다.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}