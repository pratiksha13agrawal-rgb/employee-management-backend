package com.example.employee_management.controller;

import com.example.employee_management.model.User;
import com.example.employee_management.repository.UserRepository;
import com.example.employee_management.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if(user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("user");
         }
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.save(user);
         return "User register successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        User existing =userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(existing.getEmail(), existing.getRole());
        return Map.of("token", token, "role", existing.getRole());
    }
}
