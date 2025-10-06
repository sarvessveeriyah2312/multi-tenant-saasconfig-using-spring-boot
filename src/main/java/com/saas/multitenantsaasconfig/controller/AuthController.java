package com.saas.multitenantsaasconfig.controller;

import com.saas.multitenantsaasconfig.model.UserAccount;
import com.saas.multitenantsaasconfig.repository.UserAccountRepository;
import com.saas.multitenantsaasconfig.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountRepository userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<UserAccount> register(@RequestBody UserAccount user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepo.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        var user = userRepo.findByUsername(req.get("username"))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.get("password"), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getTenantId(),
                List.of(user.getRole())
        );
        return ResponseEntity.ok(Map.of("token", token));
    }
}
