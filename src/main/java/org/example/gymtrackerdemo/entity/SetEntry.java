package org.example.gymtrackerdemo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SetEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private WorkoutEntry workoutEntry;

    private Integer setNumber;
    private Integer reps;
    private Double weight;
    private Integer duration;
}
