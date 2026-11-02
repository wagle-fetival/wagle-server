package com.wagle.wagleserver.dto;

import java.time.LocalDateTime;

public class PerformanceCreateRequest {

    private Long festivalId;
    private Long userId;
    private Long locationId;
    private String performer;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String password;

    public Long getFestivalId() { return festivalId; }
    public void setFestivalId(Long festivalId) { this.festivalId = festivalId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getLocationId() { return locationId; }
    public void setLocationId(Long locationId) { this.locationId = locationId; }

    public String getPerformer() { return performer; }
    public void setPerformer(String performer) { this.performer = performer; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}