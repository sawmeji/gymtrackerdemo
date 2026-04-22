package org.example.gymtrackerdemo.service;

import lombok.RequiredArgsConstructor;
import org.example.gymtrackerdemo.dto.AuthResponse;
import org.example.gymtrackerdemo.dto.LoginRequest;
import org.example.gymtrackerdemo.entity.User;
import org.example.gymtrackerdemo.exception.UnauthorizedException;
import org.example.gymtrackerdemo.repository.UserRepository;
import org.example.gymtrackerdemo.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        return new AuthResponse(jwtUtil.generateToken(user.getEmail()));
    }
}
