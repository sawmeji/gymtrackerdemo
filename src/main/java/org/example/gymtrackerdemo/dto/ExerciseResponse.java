package org.example.gymtrackerdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExerciseResponse {
    private Long id;
    private String name;
    private String category;
    private String createdBy;
}
