package org.example.gymtrackerdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.AddWorkoutEntryRequest;
import org.example.gymtrackerdemo.dto.StartSessionResponse;
import org.example.gymtrackerdemo.dto.WorkoutEntryResponse;
import org.example.gymtrackerdemo.entity.User;
import org.example.gymtrackerdemo.security.CustomUserDetails;
import org.example.gymtrackerdemo.service.WorkoutEntryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-entries")
@RequiredArgsConstructor
public class WorkoutEntryController {

    private final WorkoutEntryService entryService;

    // ✅ Add exercise to session
    @PostMapping("/addExercise")
    public WorkoutEntryResponse addEntry(@RequestBody AddWorkoutEntryRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return entryService.addEntry(request, userDetails.getUser());
    }


    // ✅ Get entries in session
    @GetMapping("/session/{sessionId}")
    public List<WorkoutEntryResponse> getEntries(@PathVariable Long sessionId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        return entryService.getEntries(sessionId, userDetails.getUser());
    }



}
