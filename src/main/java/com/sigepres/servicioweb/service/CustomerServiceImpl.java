package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.entities.Customer;
import com.sigepres.servicioweb.exceptions.UserNotFoundException;
import com.sigepres.servicioweb.repository.ICustomerRepository;
import com.sigepres.servicioweb.service.interfaces.ICustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository customerRepository;

    public CustomerServiceImpl(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        return  customerRepository
                .findById(customerId).orElseThrow(UserNotFoundException::new);
    }
}
