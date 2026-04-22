package org.example.gymtrackerdemo.repository;

import org.example.gymtrackerdemo.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long> {
}
