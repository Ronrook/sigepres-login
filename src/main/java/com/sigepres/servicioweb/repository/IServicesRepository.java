package com.sigepres.servicioweb.repository;


import com.sigepres.servicioweb.entities.Services;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IServicesRepository extends JpaRepository<Services, Integer> {
}
