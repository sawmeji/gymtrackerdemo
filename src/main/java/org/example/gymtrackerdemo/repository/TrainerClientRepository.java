package org.example.gymtrackerdemo.repository;

import org.example.gymtrackerdemo.entity.TrainerClientRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TrainerClientRepository extends JpaRepository<TrainerClientRelation, Long> {
    List<TrainerClientRelation> findByTrainerId(Long trainerId);

    List<TrainerClientRelation> findByClientId(Long clientId);

    boolean existsByTrainerIdAndClientId(Long trainerId, Long clientId);
}
