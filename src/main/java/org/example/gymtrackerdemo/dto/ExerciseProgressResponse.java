package org.example.gymtrackerdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExerciseProgressResponse {
    private String exerciseName;
    private List<ProgressPoint> progress;
}
