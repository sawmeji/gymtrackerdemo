package org.example.gymtrackerdemo.repository;

import org.example.gymtrackerdemo.entity.BodyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyRecordRepository extends JpaRepository<BodyRecord, Long> {
}
