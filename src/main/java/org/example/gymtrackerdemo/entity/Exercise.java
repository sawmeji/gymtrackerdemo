package org.example.gymtrackerdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String category;

    @ManyToOne
    private User createdBy;

    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    private LocalDateTime createdAt;

}
