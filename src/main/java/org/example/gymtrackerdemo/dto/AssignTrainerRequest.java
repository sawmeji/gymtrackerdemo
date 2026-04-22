package org.example.gymtrackerdemo.dto;

import lombok.Data;

@Data
public class AssignTrainerRequest {
    private Long trainerId;
    private Long clientId;
}
