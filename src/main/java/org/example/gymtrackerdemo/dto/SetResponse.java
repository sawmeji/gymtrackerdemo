package org.example.gymtrackerdemo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SetResponse {
    private Integer setNumber;
    private Integer reps;
    private Double weight;
    private Integer duration;
}
