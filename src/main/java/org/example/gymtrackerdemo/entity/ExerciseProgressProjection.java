package org.example.gymtrackerdemo.entity;

import java.time.LocalDate;

public interface ExerciseProgressProjection {
    LocalDate getDate();
    Double getTotalVolume();
}
