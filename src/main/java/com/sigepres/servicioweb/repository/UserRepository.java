package com.sigepres.servicioweb.repository;

import com.sigepres.servicioweb.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
