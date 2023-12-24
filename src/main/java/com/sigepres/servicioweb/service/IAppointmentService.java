package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.dto.AppointmentRequestDTO;
import com.sigepres.servicioweb.dto.AppointmentResponseDTO;
import com.sigepres.servicioweb.entities.Appointment;


import java.time.LocalDate;
import java.util.List;

public interface IAppointmentService {

    AppointmentResponseDTO createAppointment(AppointmentRequestDTO appointmentRequestDTO);

    AppointmentResponseDTO getAppointmentById(Integer appointmentId);

    List<AppointmentResponseDTO> getAppointmentsByEmployeeId(Integer employeeId);

    /*

    List<AppointmentResponseDTO> getAppointmentsByCustomerId(Integer customerId);

    List<AppointmentResponseDTO> getAppointmentsBetweenDates(LocalDate startDate, LocalDate endDate);

    List<AppointmentResponseDTO> getAppointmentsByCustomerAndDates(Integer customerId, LocalDate startDate, LocalDate endDate);

    List<AppointmentResponseDTO> getAppointmentsByEmployeeAndDates(Integer employeeId, LocalDate startDate, LocalDate endDate);

    List<AppointmentResponseDTO> getAppointmentsByCustomer(Integer customerId);

    AppointmentResponseDTO updateAppointment(int appointmentId, Appointment updatedAppointment);

    void deleteAppointment(int appointmentId);
*/

}
