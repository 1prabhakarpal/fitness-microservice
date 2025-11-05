package com.fitness.aiservice.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GeminiService {
  private final WebClient webClient;
  
  @Value("${gemini.api.url}")
  private String geminiApiUrl;
  @Value("${gemini.api.key}")
  private String geminiApiKey;
  
  private GeminiService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }
  
  public String getGeminiResponse(String data) {
    log.info("Sending prompt to Gemini API: {}", data);
    Map<String, Object> requestBody = Map.of(
        "contents", new Object[] {
            Map.of(
                "parts", new Object[] {
                    Map.of(
                        "text", data
                    )
                }
            )
        }
    );
    
    String response = webClient.post()
        .uri(geminiApiUrl+geminiApiKey)
        .header("content-type", "application/json")
        
        .bodyValue(requestBody)
        .retrieve()
        .bodyToMono(String.class)
        .block();
    
    return response;
  }
  
}
