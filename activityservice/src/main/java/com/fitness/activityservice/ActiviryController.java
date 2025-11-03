package com.fitness.activityservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@Log4j2
public class ActiviryController {

  private final ActivityService activityService;

  @PostMapping
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
