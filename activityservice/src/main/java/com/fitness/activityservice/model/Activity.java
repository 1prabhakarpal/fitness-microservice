package com.fitness.activityservice.model;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fitness.activityservice.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "activities")
public class Activity {

  @Id
  private String id;
  private String userId;
  private ActivityType type;
  private Integer duration;
  private Integer caloriesBurned;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  @Field("metrics")
  private Map<String, String> additionalMetrics;

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;

}
