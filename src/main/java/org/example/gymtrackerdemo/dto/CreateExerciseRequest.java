package org.example.gymtrackerdemo.dto;

import lombok.Data;

@Data
public class CreateExerciseRequest {
    private String name;
    private String category;
}
