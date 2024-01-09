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

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findByAppointmentDatetimeBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Appointment a WHERE a.customer.id = :customerId AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findAppointmentsByCustomerAndDateBetween(@Param("customerId") Integer customerId, @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Appointment a WHERE a.employee.id = :employeeId AND a.appointmentDate BETWEEN :startDate AND :endDate")
    List<Appointment> findAppointmentsByEmployeeAndDateBetween(@Param("employeeId") Integer employeeId, @Param("startDate") LocalDate startDate,
           @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Appointment a WHERE a.customer.id = :customerId AND a.appointmentDate = :date")
    List<Appointment> findAppointmentsByCustomerAndDate(@Param("customerId") Integer customerId, @Param("date") LocalDate date);

    @Query("SELECT a FROM Appointment a WHERE a.employee.id = :employeeId AND a.appointmentDate = :date")
    List<Appointment> findAppointmentsByEmployeeAndDate(@Param("employeeId") Integer employeeId, @Param("date") LocalDate date);



}

