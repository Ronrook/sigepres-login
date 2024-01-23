package com.sigepres.servicioweb.service.interfaces;

import com.sigepres.servicioweb.dto.AuthResponse;
import com.sigepres.servicioweb.dto.LoginRequest;
import com.sigepres.servicioweb.dto.RegisterRequest;
import com.sigepres.servicioweb.entities.User;

public interface ISessionService {

    AuthResponse signIn(LoginRequest loginRequest);

    //AuthResponse register(RegisterRequest request);
    AuthResponse registerEmployee(RegisterRequest request);
    AuthResponse registerCustomer(RegisterRequest request);

}
