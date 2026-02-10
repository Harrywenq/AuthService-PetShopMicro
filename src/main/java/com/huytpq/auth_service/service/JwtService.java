package com.huytpq.auth_service.service;

public interface JwtService {
    String generateToken(Long id, String email);
}
