package org.example.gymtrackerdemo.repository;

import org.example.gymtrackerdemo.entity.SetEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SetEntryRepository extends JpaRepository<SetEntry, Long> {
    List<SetEntry> findByWorkoutEntryId(Long workoutEntryId);
}
