package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.dto.AuthResponse;
import com.sigepres.servicioweb.dto.LoginRequest;
import com.sigepres.servicioweb.dto.RegisterRequest;
import com.sigepres.servicioweb.entities.Employee;
import com.sigepres.servicioweb.entities.Role;
import com.sigepres.servicioweb.entities.User;
import com.sigepres.servicioweb.exceptions.DuplicateValueException;
import com.sigepres.servicioweb.exceptions.PasswordIncorrectException;
import com.sigepres.servicioweb.exceptions.UserNotFoundException;
import com.sigepres.servicioweb.jwt.JwtService;
import com.sigepres.servicioweb.repository.IEmployeeRepository;
import com.sigepres.servicioweb.repository.IRoleRepository;
import com.sigepres.servicioweb.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio encargado del logueo de un employee
 */
@RequiredArgsConstructor
@Service
public class SessionServiceImpl implements ISessionService{

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IEmployeeRepository employeeRepository;

    /**
     * Clase que encripta contraseñas
     */
    private final PasswordEncoder passwordEncoder;

    public AuthResponse signIn(LoginRequest request) {

        UserDetails employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        // Se verifica que el password  de la request coincida con el de un empleado
        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            throw new PasswordIncorrectException();
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token=jwtService.getToken(employee);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        validateExistsDuplicateValues(request.getDniNumber(), request.getEmail());
        Optional<Role> role = roleRepository.findByName(request.getRole());

        Employee employee =  Employee.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .secondLastName(request.getSecondLastName())
                .dniNumber(request.getDniNumber())
                .birthdate(request.getBirthdate())
                .email(request.getEmail())
                .phone(request.getPhone())
                .userImage(request.getUserImage())
                .isActive(request.getIsActive())
                .password(passwordEncoder.encode( request.getPassword()))
                .role(role.orElseThrow())
                .build();

        // Persistir employee en BD
        employeeRepository.save(employee);

        return AuthResponse.builder()
                .token(jwtService.getToken(employee))
                .build();
    }

    private void validateExistsDuplicateValues(String dni, String email) {
        // Verifica si ya existe un usuario con el mismo DNI o correo electrónico
        Optional<User> existingEmployeeByDNI = userRepository.findByDniNumber(dni);
        Optional<User> existingEmployeeByEmail = userRepository.findByEmail(email);

        if (existingEmployeeByDNI.isPresent()) {
            // DNI ya está en uso, lanza una excepción
            throw new DuplicateValueException("El DNI ya está en uso");
        }

        if (existingEmployeeByEmail.isPresent()) {
            // Correo electrónico ya está en uso, lanza una excepción
            throw new DuplicateValueException("El correo electrónico ya está en uso");
        }
    }


}
