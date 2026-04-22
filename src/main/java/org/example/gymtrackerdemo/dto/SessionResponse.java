package org.example.gymtrackerdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SessionResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;
}