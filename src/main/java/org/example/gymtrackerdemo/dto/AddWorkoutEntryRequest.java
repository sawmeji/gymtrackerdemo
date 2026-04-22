package org.example.gymtrackerdemo.dto;

import lombok.Data;

@Data
public class AddWorkoutEntryRequest {
    private Long sessionId;
    private Long exerciseId;
}
