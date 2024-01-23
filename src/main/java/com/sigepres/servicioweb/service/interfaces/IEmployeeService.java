package com.sigepres.servicioweb.service.interfaces;

import com.sigepres.servicioweb.entities.Employee;

public interface IEmployeeService {

    Employee getEmployeeEntityById(Integer employeeId);
}
