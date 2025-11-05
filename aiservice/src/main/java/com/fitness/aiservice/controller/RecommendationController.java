package com.fitness.aiservice.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/recommendations")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Recommendation Controller", description = "APIs for fetching AI-based fitness recommendations")
public class RecommendationController {

  private final RecommendationService recommendationService;
  
  @Operation(summary = "Get recommendations for a user",
      description = "Fetches AI-based fitness recommendations for a specific user")
  @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully")
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Recommendation>> getRecommendationsForUser(@PathVariable String userId) {
    log.info("Fetching recommendations for user: {}", userId);
    List<Recommendation> recommendations = recommendationService.getRecommendationsForUser(userId);
    return ResponseEntity.ok(recommendations);
  }
  
  @Operation(summary = "Get recommendations for an activity",
      description = "Fetches AI-based fitness recommendations for a specific activity")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "No recommendations found for the activity")})
  @GetMapping("/activity/{activityId}")
  public ResponseEntity<Recommendation> getRecommendationsForActivity(@PathVariable String activityId) {
    log.info("Fetching recommendations for activity: {}", activityId);
    Recommendation recommendations = recommendationService.getRecommendationsForActivity(activityId);
    if (recommendations == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(recommendations);
  }
}