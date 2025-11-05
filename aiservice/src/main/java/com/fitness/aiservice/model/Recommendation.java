package com.fitness.aiservice.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recommendations")
public class Recommendation {
  
  @Id
  String id;
  String activityId;
  String userId;
  String activityType;
  String recommendationText;
  private List<String> improvements;
  private List<String> suggestions;
  
  @CreatedDate
  private LocalDateTime createdAt;
}
