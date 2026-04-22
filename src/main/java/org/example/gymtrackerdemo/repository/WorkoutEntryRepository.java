package org.example.gymtrackerdemo.repository;

import org.example.gymtrackerdemo.entity.WorkoutEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {
    List<WorkoutEntry> findBySessionId(Long sessionId);
}
