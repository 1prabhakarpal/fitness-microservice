package com.fitness.activityservice.service;

import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
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
  private final UserValidationService userValidationService;
  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange.name}")
  private String exchange;
  @Value("${rabbitmq.routing.key}")
  private String routingKey;

  public ActivityResponse trackActivity(ActivityRequest request) {

    if (!userValidationService.isUserValid(request.userId())) {
      log.warn("Attempted to track activity for invalid userId: {}", request.userId());
      throw new RuntimeException("Invalid user ID: " + request.userId());
    }

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

    // Publish event to RabbitMQ for AI processing
    try {
      rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
      log.info("Published activity {} to RabbitMQ for AI processing", savedActivity.getId());
    } catch (Exception e) {
      log
          .error("Failed to publish activity {} to RabbitMQ: {}", savedActivity.getId(),
                 e.getMessage());
    }

    log.info("Tracked activity: {}", savedActivity);
    return new ActivityResponse(savedActivity.getId(), savedActivity.getUserId(),
        savedActivity.getType(), savedActivity.getDuration(), savedActivity.getCaloriesBurned(),
        savedActivity.getStartTime(), savedActivity.getEndTime(),
        savedActivity.getAdditionalMetrics(), savedActivity.getCreatedAt(),
        savedActivity.getUpdatedAt());
  }


  public List<ActivityResponse> getUserActivities(String userId) {
    List<Activity> activities = activityRepository.findByUserId(userId);
    log.info("Retrieved {} activities for user {}", activities.size(), userId);
    return activities
        .stream()
          .map(activity -> new ActivityResponse(activity.getId(), activity.getUserId(),
              activity.getType(), activity.getDuration(), activity.getCaloriesBurned(),
              activity.getStartTime(), activity.getEndTime(), activity.getAdditionalMetrics(),
              activity.getCreatedAt(), activity.getUpdatedAt()))
          .toList();
  }


  public ActivityResponse getActivityById(String activityId) {
    Activity activity = activityRepository.findById(activityId).orElse(null);
    log.info("Retrieved activity by id {}: {}", activityId, activity);
    return activity == null ? null
        : new ActivityResponse(activity.getId(), activity.getUserId(), activity.getType(),
            activity.getDuration(), activity.getCaloriesBurned(), activity.getStartTime(),
            activity.getEndTime(), activity.getAdditionalMetrics(), activity.getCreatedAt(),
            activity.getUpdatedAt());
  }

}
