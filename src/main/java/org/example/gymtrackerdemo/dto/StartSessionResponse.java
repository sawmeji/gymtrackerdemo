package org.example.gymtrackerdemo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StartSessionResponse {
    private Long sessionId;
    private LocalDateTime startTime;
}