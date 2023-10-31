package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.dto.AuthResponse;
import com.sigepres.servicioweb.dto.LoginRequest;
import com.sigepres.servicioweb.dto.RegisterRequest;

public interface ISessionService {

    AuthResponse signIn(LoginRequest loginRequest);

    AuthResponse register(RegisterRequest request);
}
