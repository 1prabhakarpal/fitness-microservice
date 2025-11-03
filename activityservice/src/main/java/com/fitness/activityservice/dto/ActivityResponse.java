package com.fitness.activityservice.dto;

import java.time.LocalDateTime;
import java.util.Map;
import com.fitness.activityservice.enums.ActivityType;

public record ActivityResponse(String id, String userId, ActivityType activityType,
    Integer duration, Integer caloriesBurned, LocalDateTime startTime, LocalDateTime endTime,
    Map<String, String> additionalMetrics, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
