package org.example.gymtrackerdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.UserCreateRequest;
import org.example.gymtrackerdemo.dto.UserResponse;
import org.example.gymtrackerdemo.entity.Role;
import org.example.gymtrackerdemo.entity.TrainerClientRelation;
import org.example.gymtrackerdemo.entity.User;
import org.example.gymtrackerdemo.entity.ActiveStatus;
import org.example.gymtrackerdemo.exception.ConflictException;
import org.example.gymtrackerdemo.exception.ForbiddenException;
import org.example.gymtrackerdemo.exception.NotFoundException;
import org.example.gymtrackerdemo.repository.TrainerClientRepository;
import org.example.gymtrackerdemo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TrainerClientRepository relationRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // ✅ Create user
    public UserResponse createUser(UserCreateRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new ConflictException("This email is already used");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(Role.valueOf(request.getRole()))
                .createdAt(LocalDateTime.now())
                .status(ActiveStatus.ACTIVE)
                .build();

        userRepository.save(user);

        return mapToResponse(user);
    }

    // ✅ Get user
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return mapToResponse(user);
    }

    // 🔁 Mapper
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
    }

    // ✅ Assign trainer to client
    public void assignTrainer(Long trainerId, Long clientId) {

        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new NotFoundException("Trainer not found"));

        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        // ✅ Role validation
        if (trainer.getRole() != Role.TRAINER) {
            throw new ForbiddenException("User is not a trainer");
        }

        if (client.getRole() != Role.CLIENT) {
            throw new ForbiddenException("User is not a client");
        }

        // ✅ Prevent duplicate
        if (relationRepository.existsByTrainerIdAndClientId(trainerId, clientId)) {
            throw new ConflictException("Relation already exists");
        }

        TrainerClientRelation relation = TrainerClientRelation.builder()
                .trainer(trainer)
                .client(client)
                .build();

        relationRepository.save(relation);
    }

    // ✅ Get trainer's clients
    public List<UserResponse> getClients(Long trainerId) {
        return relationRepository.findByTrainerId(trainerId)
                .stream()
                .map(rel -> UserResponse.builder()
                        .id(rel.getClient().getId())
                        .email(rel.getClient().getEmail())
                        .name(rel.getClient().getEmail())
                        .build())
                .toList();
    }

    // ✅ Get client's trainer
    public List<UserResponse> getTrainers(Long clientId) {
        return relationRepository.findByClientId(clientId)
                .stream()
                .map(rel -> UserResponse.builder()
                        .id(rel.getTrainer().getId())
                        .email(rel.getTrainer().getEmail())
                        .name(rel.getTrainer().getName())
                        .build())
                .toList();
    }
}
