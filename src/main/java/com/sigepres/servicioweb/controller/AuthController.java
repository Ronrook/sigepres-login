package com.sigepres.servicioweb.controller;

import com.sigepres.servicioweb.dto.LoginRequest;
import com.sigepres.servicioweb.dto.UserRegisterRequest;
import com.sigepres.servicioweb.entities.User;
import com.sigepres.servicioweb.repository.IUserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok("Acceso permitido");
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserRegisterRequest> register( @Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(request);
    }

}

