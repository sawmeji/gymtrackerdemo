package org.example.gymtrackerdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.CreateExerciseRequest;
import org.example.gymtrackerdemo.dto.ExerciseResponse;
import org.example.gymtrackerdemo.security.CustomUserDetails;
import org.example.gymtrackerdemo.service.ExerciseService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping("/create")
    public ExerciseResponse create(
            @RequestBody CreateExerciseRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return exerciseService.create(request, userDetails.getUser());
    }

    @GetMapping("/getAll")
    public List<ExerciseResponse> getAll() {
        return exerciseService.getAll();
    }
}
