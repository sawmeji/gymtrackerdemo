package org.example.gymtrackerdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionSummaryResponse {
    private Long sessionId;
    private Integer totalExercises;
    private Integer totalSets;
    private Double totalVolume;
    private Integer totalDuration;
}
