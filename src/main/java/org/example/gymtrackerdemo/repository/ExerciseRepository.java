package org.example.gymtrackerdemo.repository;

import org.example.gymtrackerdemo.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    boolean existsByNameIgnoreCase(String name);
}
