package com.wagle.backend.controller;

import com.wagle.backend.entity.Location;
import com.wagle.backend.service.LocationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> getLocation(@PathVariable Long locationId) {
        Location location = locationService.getLocation(locationId)
                .orElseThrow(() -> new RuntimeException("위치 정보를 찾을 수 없습니다."));
        return ResponseEntity.ok(location);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}