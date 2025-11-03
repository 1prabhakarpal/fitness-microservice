package com.fitness.activityservice.service;

import org.springframework.stereotype.Service;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ActivityService {

  private final ActivityRepository activityRepository;


  public ActivityResponse trackActivity(ActivityRequest request) {

    Activity activity = Activity
        .builder()
          .userId(request.userId())
          .type(request.activityType())
          .duration(request.duration())
          .caloriesBurned(request.caloriesBurned())
          .startTime(request.startTime())
          .endTime(request.endTime())
          .additionalMetrics(request.additionalMetrics())
          .build();
    Activity savedActivity = activityRepository.save(activity);
    log.info("Tracked activity: {}", savedActivity);
    return new ActivityResponse(savedActivity.getId(), savedActivity.getUserId(),
        savedActivity.getType(), savedActivity.getDuration(), savedActivity.getCaloriesBurned(),
        savedActivity.getStartTime(), savedActivity.getEndTime(),
        savedActivity.getAdditionalMetrics(), savedActivity.getCreatedAt(),
        savedActivity.getUpdatedAt());
  }

}
