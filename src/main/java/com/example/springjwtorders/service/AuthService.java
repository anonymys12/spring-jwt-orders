package com.example.springjwtorders.service;

import com.example.springjwtorders.entity.dto.AuthRequest;
import com.example.springjwtorders.entity.dto.AuthResponse;
import com.example.springjwtorders.entity.Role;
import com.example.springjwtorders.entity.User;
import com.example.springjwtorders.exception.NotFoundException;
import com.example.springjwtorders.repository.UserRepository;
import com.example.springjwtorders.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse register(AuthRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String access = jwtUtil.generateToken(user.getUsername(), false);
        String refresh = jwtUtil.generateToken(user.getUsername(), true);
        return new AuthResponse(access, refresh);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String access = jwtUtil.generateToken(user.getUsername(), false);
        String refresh = jwtUtil.generateToken(user.getUsername(), true);
        return new AuthResponse(access, refresh);
    }
}
