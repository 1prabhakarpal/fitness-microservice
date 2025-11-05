package com.fitness.aiservice.model;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {

  private String id;
  private String userId;
  private Integer duration;
  private Integer caloriesBurned;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Map<String, String> additionalMetrics;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
