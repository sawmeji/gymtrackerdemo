package org.example.gymtrackerdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.ExerciseProgressResponse;
import org.example.gymtrackerdemo.dto.ProgressPoint;
import org.example.gymtrackerdemo.dto.SessionSummaryResponse;
import org.example.gymtrackerdemo.entity.*;
import org.example.gymtrackerdemo.exception.ForbiddenException;
import org.example.gymtrackerdemo.exception.NotFoundException;
import org.example.gymtrackerdemo.repository.SetEntryRepository;
import org.example.gymtrackerdemo.repository.WorkoutEntryRepository;
import org.example.gymtrackerdemo.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final SetEntryRepository setRepository;
    private final WorkoutEntryRepository entryRepository;
    private final WorkoutSessionRepository sessionRepository;

    public ExerciseProgressResponse getExerciseProgress(Long exerciseId, User user) {
        List<WorkoutEntry> entries = entryRepository.findAll();

        Map<LocalDate, Double> progressMap = new HashMap<>();

        for (WorkoutEntry entry : entries) {

            if (!entry.getSession().getUser().getId().equals(user.getId())) continue;
            if (!entry.getExercise().getId().equals(exerciseId)) continue;

            LocalDate date = entry.getSession().getStartTime().toLocalDate();

            List<SetEntry> sets = setRepository.findByWorkoutEntryId(entry.getId());

            double totalVolume = sets.stream()
                    .filter(s -> s.getReps() != null && s.getWeight() != null)
                    .mapToDouble(s -> s.getReps() * s.getWeight())
                    .sum();

            progressMap.put(date,
                    progressMap.getOrDefault(date, 0.0) + totalVolume);
        }

        List<ProgressPoint> result = progressMap.entrySet().stream()
                .map(e -> ProgressPoint.builder()
                        .date(e.getKey())
                        .totalVolume(e.getValue())
                        .build())
                .sorted(Comparator.comparing(ProgressPoint::getDate))
                .toList();

        return ExerciseProgressResponse.builder()
                .exerciseName("Exercise") // can improve
                .progress(result)
                .build();
    }

    public SessionSummaryResponse getSessionSummary(Long sessionId, User user) {
        WorkoutSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found"));

        if (!session.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("Not your session");
        }

        List<WorkoutEntry> entries = entryRepository.findBySessionId(sessionId);

        int totalSets = 0;
        double totalVolume = 0;
        int totalDuration = 0;

        for (WorkoutEntry entry : entries) {

            List<SetEntry> sets = setRepository.findByWorkoutEntryId(entry.getId());

            totalSets += sets.size();

            for (SetEntry s : sets) {
                if (s.getReps() != null && s.getWeight() != null) {
                    totalVolume += s.getReps() * s.getWeight();
                }
                if (s.getDuration() != null) {
                    totalDuration += s.getDuration();
                }
            }
        }

        return SessionSummaryResponse.builder()
                .sessionId(sessionId)
                .totalExercises(entries.size())
                .totalSets(totalSets)
                .totalVolume(totalVolume)
                .totalDuration(totalDuration)
                .build();
    }



}
