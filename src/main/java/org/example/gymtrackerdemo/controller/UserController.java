package org.example.gymtrackerdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.AssignTrainerRequest;
import org.example.gymtrackerdemo.dto.UserCreateRequest;
import org.example.gymtrackerdemo.dto.UserResponse;
import org.example.gymtrackerdemo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ✅ Create user
    @PostMapping("/create")
    public UserResponse createUser(@RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    // ✅ Get user
    @GetMapping("/getUser/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    // ✅ Assign trainer
    @PostMapping("/assign-trainer")
    public String assignTrainer(@RequestBody AssignTrainerRequest request) {
        userService.assignTrainer(request.getTrainerId(), request.getClientId());
        return "Trainer assigned successfully";
    }

    // ✅ Get clients of trainer
    @GetMapping("/{trainerId}/clients")
    public List<UserResponse> getClients(@PathVariable Long trainerId) {
        return userService.getClients(trainerId);
    }

    // ✅ Get trainers of client
    @GetMapping("/{clientId}/trainers")
    public List<UserResponse> getTrainers(@PathVariable Long clientId) {
        return userService.getTrainers(clientId);
    }
}
