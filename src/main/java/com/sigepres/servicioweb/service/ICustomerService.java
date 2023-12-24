package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.entities.Customer;

public interface ICustomerService {

    Customer getCustomerById(Integer customerId);
}
