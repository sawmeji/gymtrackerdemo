package org.example.gymtrackerdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkoutEntryResponse {
    private Long entryId;
    private String exerciseName;
    private String category;
}
