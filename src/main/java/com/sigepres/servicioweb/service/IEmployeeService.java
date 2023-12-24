package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.entities.Employee;

public interface IEmployeeService {

    Employee getEmployeeById(Integer employeeId);
}
