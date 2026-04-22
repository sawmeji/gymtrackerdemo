package org.example.gymtrackerdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.AddWorkoutEntryRequest;
import org.example.gymtrackerdemo.dto.WorkoutEntryResponse;
import org.example.gymtrackerdemo.entity.Exercise;
import org.example.gymtrackerdemo.entity.User;
import org.example.gymtrackerdemo.entity.WorkoutEntry;
import org.example.gymtrackerdemo.entity.WorkoutSession;
import org.example.gymtrackerdemo.exception.ForbiddenException;
import org.example.gymtrackerdemo.exception.NotFoundException;
import org.example.gymtrackerdemo.repository.ExerciseRepository;
import org.example.gymtrackerdemo.repository.WorkoutEntryRepository;
import org.example.gymtrackerdemo.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutEntryService {

    private final WorkoutEntryRepository entryRepository;
    private final WorkoutSessionRepository sessionRepository;
    private final ExerciseRepository exerciseRepository;

    // ✅ Add exercise to session
    public WorkoutEntryResponse addEntry(AddWorkoutEntryRequest request, User user) {

        // 1. Find session
        WorkoutSession session = sessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new NotFoundException("Session not found"));

        // 🔐 Security check
        if (!session.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("Not your session");
        }

        // 2. Find exercise
        Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new NotFoundException("Exercise not found"));

        // 3. Create entry
        WorkoutEntry entry = WorkoutEntry.builder()
                .session(session)
                .exercise(exercise)
                .build();

        entryRepository.save(entry);

        // 4. Response
        return WorkoutEntryResponse.builder()
                .entryId(entry.getId())
                .exerciseName(exercise.getName())
                .category(exercise.getCategory())
                .build();
    }

    // ✅ Get all exercises in a session
    public List<WorkoutEntryResponse> getEntries(Long sessionId, User user) {

        WorkoutSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found"));

        // 🔐 Security check
        if (!session.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("Not your session");
        }

        return entryRepository.findBySessionId(sessionId)
                .stream()
                .map(entry -> WorkoutEntryResponse.builder()
                        .entryId(entry.getId())
                        .exerciseName(entry.getExercise().getName())
                        .category(entry.getExercise().getCategory())
                        .build())
                .toList();
    }
}
