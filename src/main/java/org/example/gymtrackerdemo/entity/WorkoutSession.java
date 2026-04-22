package org.example.gymtrackerdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<WorkoutEntry> workoutEntries;
}
