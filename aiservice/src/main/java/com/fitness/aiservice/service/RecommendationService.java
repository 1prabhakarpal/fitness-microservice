package com.fitness.aiservice.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repo.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class RecommendationService {

  private final RecommendationRepository recommendationRepository;

  public List<Recommendation> getRecommendationsForUser(String userId) {
     log.info("Retrieving recommendations for user: {}", userId);
      return recommendationRepository.findByUserId(userId);
  }

  public Recommendation getRecommendationsForActivity(String activityId) {
    log.info("Retrieving recommendations for activity: {}", activityId);
    return recommendationRepository.findByActivityId(activityId).orElse(null);
  }

}
