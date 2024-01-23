package com.sigepres.servicioweb.controller;

import com.sigepres.servicioweb.dto.AuthResponse;
import com.sigepres.servicioweb.dto.LoginRequest;
import com.sigepres.servicioweb.dto.RegisterRequest;


import com.sigepres.servicioweb.service.interfaces.ISessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ISessionService sessionService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(sessionService.signIn(request));
    }

    /*@PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(sessionService.register(request));
    }*/

    @PostMapping(value = "/register/employee")
    public ResponseEntity<AuthResponse> registerEmployee(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(sessionService.registerEmployee(request));
    }
    @PostMapping(value = "/register/customer")
    public ResponseEntity<AuthResponse> registerCustomer(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(sessionService.registerCustomer(request));
    }
}

