package org.example.gymtrackerdemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private List<WorkoutSession> sessions;

    @OneToMany(mappedBy = "user")
    private List<BodyRecord> bodyRecords;

    @OneToMany(mappedBy = "trainer")
    private List<TrainerClientRelation> clients;

    @OneToMany(mappedBy = "client")
    private List<TrainerClientRelation> trainers;

    @OneToMany(mappedBy = "user")
    private List<GroupMember> groupMemberships;
}