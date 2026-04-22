package org.example.gymtrackerdemo.controller;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.AuthResponse;
import org.example.gymtrackerdemo.dto.LoginRequest;
import org.example.gymtrackerdemo.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
