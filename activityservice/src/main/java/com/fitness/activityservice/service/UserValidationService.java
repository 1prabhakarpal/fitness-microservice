package com.fitness.activityservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserValidationService {
  private final WebClient userServiceWebClient;

  public boolean isUserValid(String userId) {
    try {
      Boolean isValid = userServiceWebClient
          .get()
            .uri("/api/users/{userId}/validate", userId)
            .retrieve()
            .bodyToMono(Boolean.class)
            .block();
      log.info("User validation for userId {}: {}", userId, isValid);
      return Boolean.TRUE.equals(isValid);
    } catch (WebClientResponseException e) {
      if (e.getStatusCode().value() == 404) {
        log.warn("User with userId {} not found", userId);
        return false;
      }
      log.error("Error validating user with userId {}: {}", userId, e.getMessage());
      throw new RuntimeException("Error validating user", e);
    }
  }
}
