package org.example.gymtrackerdemo.repository;

import org.example.gymtrackerdemo.entity.Role;
import org.example.gymtrackerdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    boolean existsByEmail(String username);

}
