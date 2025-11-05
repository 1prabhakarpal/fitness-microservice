package com.fitness.aiservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ActivityMessageListner {
  
  private final ActivityAIService activityAIService;
  
  @RabbitListener(queues = "${rabbitmq.queue.name}")
  public void processActivity(Activity activity) {
    log.info("Recieved Activity for processing: {}", activity.getId());
    log.info("Generated Recommendations: {}", activityAIService.generateRecommendations(activity));
  }
}
