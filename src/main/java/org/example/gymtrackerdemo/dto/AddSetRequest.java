package org.example.gymtrackerdemo.dto;

import lombok.Data;

@Data
public class AddSetRequest {
    private Long workoutEntryId;
    private Integer reps;
    private Double weight;
    private Integer duration;
}
