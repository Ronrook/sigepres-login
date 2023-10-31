package com.sigepres.servicioweb.repository;

import com.sigepres.servicioweb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByDniNumber(String dniNumber);

    //Optional<User> findByUsername(String username);
}

