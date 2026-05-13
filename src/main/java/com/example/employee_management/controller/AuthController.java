package com.example.employee_management.controller;

import com.example.employee_management.model.User;
import com.example.employee_management.repository.UserRepository;
import com.example.employee_management.utility.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User existing = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if(!passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid password"));
            }

            if(!existing.isActive()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("message", "Account deactivated! Contact admin."));
            }

            String token = jwtUtil.generateToken(existing.getEmail(), existing.getRole());
            return ResponseEntity.ok(Map.of("token", token, "role", existing.getRole()));
            
        } catch(Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
