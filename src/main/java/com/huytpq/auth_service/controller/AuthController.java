package com.huytpq.auth_service.controller;

import com.huytpq.auth_service.dto.input.LoginInput;
import com.huytpq.auth_service.dto.output.UserOutput;
import com.huytpq.auth_service.feign.UserClient;
import com.huytpq.auth_service.service.JwtService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserClient userClient;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginInput input) {
        try {
            UserOutput user = userClient.validateLogin(input);

            String token = jwtService.generateToken(user.getId(), user.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        }
        catch (FeignException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
}
