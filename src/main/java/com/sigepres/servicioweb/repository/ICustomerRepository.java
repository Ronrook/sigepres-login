package com.sigepres.servicioweb.repository;

import com.sigepres.servicioweb.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository <Customer, Integer> {
}
