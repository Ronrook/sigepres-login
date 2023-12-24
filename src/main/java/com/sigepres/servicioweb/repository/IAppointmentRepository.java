package com.sigepres.servicioweb.repository;

import com.sigepres.servicioweb.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {

}

