package org.example.gymtrackerdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.ExerciseProgressResponse;
import org.example.gymtrackerdemo.dto.SessionSummaryResponse;
import org.example.gymtrackerdemo.security.CustomUserDetails;
import org.example.gymtrackerdemo.service.AnalyticsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    // ✅ Exercise progress
    @GetMapping("/exercise/{exerciseId}")
    public ExerciseProgressResponse getExerciseProgress(@PathVariable Long exerciseId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return analyticsService.getExerciseProgress(exerciseId, userDetails.getUser());
    }

    // ✅ Session summary
    @GetMapping("/session/{sessionId}")
    public SessionSummaryResponse getSessionSummary(@PathVariable Long sessionId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return analyticsService.getSessionSummary(sessionId, userDetails.getUser());
    }

}
