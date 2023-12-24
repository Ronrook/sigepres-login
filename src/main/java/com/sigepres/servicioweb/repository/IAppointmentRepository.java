package com.sigepres.servicioweb.repository;

import com.sigepres.servicioweb.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.sql.Time;
import java.time.LocalDate;
import java.util.List;


public interface IAppointmentRepository extends JpaRepository<Appointment, Integer> {


    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a WHERE a.employee.id = :employeeId " +
            "AND a.appointmentDate = :appointmentDate AND a.appointmentStartTime <= :appointmentEndTime AND a.appointmentEndTime >= :appointmentStartTime")
    boolean existsByEmployeeIdAndAppointmentDateAndTimeRange( @Param("employeeId") Integer employeeId, @Param("appointmentDate") LocalDate appointmentDate,
            @Param("appointmentStartTime") Time appointmentStartTime, @Param("appointmentEndTime") Time appointmentEndTime);

    List<Appointment> findByEmployeeId(Integer employeeId);
    List<Appointment> findByCustomerId(Integer customerId);
}

