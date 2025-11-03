package com.fitness.activityservice.dto;

import java.time.LocalDateTime;
import java.util.Map;
import com.fitness.activityservice.enums.ActivityType;

public record ActivityRequest(String userId, ActivityType activityType, Integer duration,
    LocalDateTime startTime, LocalDateTime endTime, Integer caloriesBurned,
    Map<String, String> additionalMetrics) {

}
