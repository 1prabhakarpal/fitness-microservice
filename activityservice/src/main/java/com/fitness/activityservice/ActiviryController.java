package com.fitness.activityservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@Log4j2
@Tag(name = "Activity Service", description = "APIs for tracking fitness activities")
public class ActiviryController {

  private final ActivityService activityService;

  @PostMapping
  @Operation(summary = "Track a new activity",
      description = "Tracks a new fitness activity for a user")
  @ApiResponses(
      value = {@ApiResponse(responseCode = "200", description = "Activity tracked successfully"),
          @ApiResponse(responseCode = "500", description = "Internal server error")})
  public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request) {
    // Implementation goes here
    try {
      return ResponseEntity.ok(activityService.trackActivity(request));
    } catch (Exception e) {
      log.error("Error tracking activity: {}", e.getMessage());
      return ResponseEntity.status(500).build();
    }
  }
}
