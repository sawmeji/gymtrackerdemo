package org.example.gymtrackerdemo.repository;

import org.example.gymtrackerdemo.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
