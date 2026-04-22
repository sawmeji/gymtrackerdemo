package org.example.gymtrackerdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.SessionResponse;
import org.example.gymtrackerdemo.dto.StartSessionResponse;
import org.example.gymtrackerdemo.entity.User;
import org.example.gymtrackerdemo.entity.WorkoutSession;
import org.example.gymtrackerdemo.exception.ForbiddenException;
import org.example.gymtrackerdemo.exception.NotFoundException;
import org.example.gymtrackerdemo.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutSessionService {

    private final WorkoutSessionRepository sessionRepository;

    // ✅ Start session
    public StartSessionResponse startSession(User user) {

        WorkoutSession session = WorkoutSession.builder()
                .user(user)
                .startTime(LocalDateTime.now())
                .build();

        sessionRepository.save(session);

        return StartSessionResponse.builder()
                .sessionId(session.getId())
                .startTime(session.getStartTime())
                .build();
    }

    // ✅ End session
    public SessionResponse endSession(Long sessionId, User user) {

        WorkoutSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new NotFoundException("Session not found"));

        // 🔐 Security check
        if (!session.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("Not your session");
        }

        session.setEndTime(LocalDateTime.now());

        int duration = (int) java.time.Duration.between(
                session.getStartTime(),
                session.getEndTime()
        ).toMinutes();

        session.setDuration(duration);

        sessionRepository.save(session);

        return mapToResponse(session);
    }

    // ✅ Get my sessions
    public List<SessionResponse> getMySessions(User user) {

        return sessionRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔁 Mapper
    private SessionResponse mapToResponse(WorkoutSession session) {
        return SessionResponse.builder()
                .id(session.getId())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .duration(session.getDuration())
                .build();
    }
}
