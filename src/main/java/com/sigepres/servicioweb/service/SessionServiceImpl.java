package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.dto.AuthResponse;
import com.sigepres.servicioweb.dto.LoginRequest;
import com.sigepres.servicioweb.dto.RegisterRequest;
import com.sigepres.servicioweb.entities.Customer;
import com.sigepres.servicioweb.entities.Employee;
import com.sigepres.servicioweb.entities.Role;
import com.sigepres.servicioweb.entities.User;
import com.sigepres.servicioweb.exceptions.DuplicateValueException;
import com.sigepres.servicioweb.exceptions.PasswordIncorrectException;
import com.sigepres.servicioweb.exceptions.UserNotFoundException;
import com.sigepres.servicioweb.jwt.JwtService;
import com.sigepres.servicioweb.repository.ICustomerRepository;
import com.sigepres.servicioweb.repository.IEmployeeRepository;
import com.sigepres.servicioweb.repository.IRoleRepository;
import com.sigepres.servicioweb.repository.IUserRepository;
import com.sigepres.servicioweb.service.interfaces.ISessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio encargado del logueo de un employee
 */

@Service
public class SessionServiceImpl implements ISessionService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IEmployeeRepository employeeRepository;
    private final ICustomerRepository customerRepository;
    @Autowired
    private final ModelMapper modelMapper;

    /**
     * Clase que encripta contraseñas
     */
    private final PasswordEncoder passwordEncoder;

    public SessionServiceImpl(IUserRepository userRepository, IRoleRepository roleRepository, JwtService jwtService, AuthenticationManager authenticationManager, IEmployeeRepository employeeRepository, ICustomerRepository customerRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
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

    @Override
    public AuthResponse registerEmployee(RegisterRequest request) {
        validateExistsDuplicateValues(request.getDniNumber(), request.getEmail());
        Optional<Role> role = roleRepository.findByName(request.getRole());

        User user =  registerUser(request);
        Employee employee =  modelMapper.map(user, Employee.class);
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setRole(role.orElseThrow());

        // Persistir employee en BD
        employeeRepository.save(employee);

        return AuthResponse.builder()
                .token(jwtService.getToken(employee))
                .build();
    }


    @Override
    public AuthResponse registerCustomer(RegisterRequest request) {
        User user =  registerUser(request);
        Customer customer =  modelMapper.map(user,Customer.class);
        // Persistir customer en BD
        customerRepository.save(customer);

        return AuthResponse.builder()
                .token("Cliente registrado con exito")
                .build();
    }

    public User registerUser(RegisterRequest request) {

        validateExistsDuplicateValues(request.getDniNumber(), request.getEmail());

        return User.builder()
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
                .build();
    }


    // Verifica si ya existe un usuario con el mismo DNI o correo electrónico
    private void validateExistsDuplicateValues(String dni, String email) {

        if (userRepository.existsByDniNumber(dni)) {
            // DNI ya está en uso, lanza una excepción
            throw new DuplicateValueException("El DNI ya está en uso");
        }

        if (userRepository.existsByEmail(email)) {
            // Correo electrónico ya está en uso, lanza una excepción
            throw new DuplicateValueException("El correo electrónico ya está en uso");
        }
    }
}
