package com.sigepres.servicioweb.repository;

import com.sigepres.servicioweb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
    boolean existsByDniNumber(String dniNumber);
}

