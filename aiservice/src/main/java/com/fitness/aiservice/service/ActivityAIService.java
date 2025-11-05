package com.fitness.aiservice.service;

import org.springframework.stereotype.Service;
import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ActivityAIService {
  private final GeminiService geminiService;
  
  public String generateRecommendations(Activity activity) {
    log.info("Generating activity recommendations for user data: {}", activity.getId());
    String prompt = createPromptForActivity(activity);
    String response = geminiService.getGeminiResponse(prompt);
    log.info("Received recommendations from Gemini API :{}", response);
    return response;
  }

  private String createPromptForActivity(Activity activity) {
    return String.format("""
                         Analyze the fitness activity and respond **only** in the following exact JSON structure.
                         Do not include any text outside the JSON. Do not add additional fields. Do not leave any field empty.

                         {
                           "analysis": {
                             "overall": "High-level summary of the activity",
                             "pace": "Detailed pace analysis or effort level based on duration and type",
                             "heartRate": "Interpret heart rate or intensity (use context from additionalMetrics if available)",
                             "caloriesBurned": "Evaluate calories burned relative to duration and activity type"
                           },
                           "improvements": [
                             {
                               "area": "Specific area of improvement",
                               "recommendation": "Detailed, actionable improvement suggestion"
                             }
                           ],
                           "suggestions": [
                             {
                               "workout": "Suggested workout or training focus",
                               "description": "Detailed explanation of how to perform or integrate this workout"
                             }
                           ],
                           "safety": [
                             "Safety advice 1",
                             "Safety advice 2"
                           ]
                         }

                         Now analyze this activity:

                         Activity Type: %s
                         Duration: %d minutes
                         Calories Burned: %d
                         Additional Metrics: %s

                         Your analysis should provide:
                         - Performance insights and what the numbers imply
                         - Improvement points with reasons
                         - Training suggestions for upcoming workouts
                         - Safety reminders based on activity type and effort

                         Remember: **Return ONLY valid JSON that matches the structure above.**
                         """,
                         activity.getType(),
                         activity.getDuration(),
                         activity.getCaloriesBurned(),
                         activity.getAdditionalMetrics()
                 );

  };
}
