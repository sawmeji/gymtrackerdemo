package org.example.gymtrackerdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkoutEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private WorkoutSession session;

    @ManyToOne
    private Exercise exercise;

    @OneToMany(mappedBy = "workoutEntry", cascade = CascadeType.ALL)
    private List<SetEntry> sets;

}
