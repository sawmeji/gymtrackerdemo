package org.example.gymtrackerdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProgressPoint {
    private LocalDate date;
    private Double totalVolume;
}
