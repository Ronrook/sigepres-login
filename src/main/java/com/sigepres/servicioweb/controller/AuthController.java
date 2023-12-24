package com.sigepres.servicioweb.controller;

import com.sigepres.servicioweb.dto.AuthResponse;
import com.sigepres.servicioweb.dto.LoginRequest;
import com.sigepres.servicioweb.dto.RegisterRequest;

import com.sigepres.servicioweb.service.SessionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SessionServiceImpl sessionService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(sessionService.signIn(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(sessionService.register(request));
    }
}

