package org.example.gymtrackerdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.CreateExerciseRequest;
import org.example.gymtrackerdemo.dto.ExerciseResponse;
import org.example.gymtrackerdemo.entity.ActiveStatus;
import org.example.gymtrackerdemo.entity.Exercise;
import org.example.gymtrackerdemo.entity.User;
import org.example.gymtrackerdemo.exception.ConflictException;
import org.example.gymtrackerdemo.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    // ✅ Create Exercise
    public ExerciseResponse create(CreateExerciseRequest request, User user) {

        // 1. Prevent duplicate
        if (exerciseRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ConflictException("Exercise already exists");
        }

        // 2. Create entity
        Exercise exercise = Exercise.builder()
                .name(request.getName())
                .category(request.getCategory())
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .status(ActiveStatus.ACTIVE)
                .build();

        // 3. Save
        exerciseRepository.save(exercise);

        // 4. Return response
        return mapToResponse(exercise);
    }

    // ✅ Get all exercises
    public List<ExerciseResponse> getAll() {
        return exerciseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔁 Mapper
    private ExerciseResponse mapToResponse(Exercise exercise) {
        return ExerciseResponse.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .category(exercise.getCategory())
                .createdBy(
                        exercise.getCreatedBy() != null
                                ? exercise.getCreatedBy().getEmail()
                                : "SYSTEM"
                )
                .build();
    }
}
