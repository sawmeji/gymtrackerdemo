package org.example.gymtrackerdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.SessionResponse;
import org.example.gymtrackerdemo.dto.StartSessionResponse;
import org.example.gymtrackerdemo.security.CustomUserDetails;
import org.example.gymtrackerdemo.service.WorkoutSessionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class WorkoutSessionController {

    private final WorkoutSessionService sessionService;

    @PostMapping("/start")
    public StartSessionResponse startSession(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return sessionService.startSession(userDetails.getUser());
    }

    @PostMapping("/{sessionId}/end")
    public SessionResponse endSession(
            @PathVariable Long sessionId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return sessionService.endSession(sessionId, userDetails.getUser());
    }

    @GetMapping("/me")
    public List<SessionResponse> getMySessions(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return sessionService.getMySessions(userDetails.getUser());
    }
}
