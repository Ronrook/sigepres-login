package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.entities.Employee;
import com.sigepres.servicioweb.exceptions.ResourceNotFoundException;
import com.sigepres.servicioweb.repository.IEmployeeRepository;
import com.sigepres.servicioweb.service.interfaces.IEmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository employeeRepository;

    public EmployeeServiceImpl(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getEmployeeEntityById(Integer employeeId) {
        return employeeRepository
                .findById(employeeId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("El Empleado ingresado no existe."));
    }
}
