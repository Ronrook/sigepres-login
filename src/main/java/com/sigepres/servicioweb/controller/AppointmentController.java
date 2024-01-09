package com.sigepres.servicioweb.controller;

import com.sigepres.servicioweb.dto.AppointmentRequestDTO;
import com.sigepres.servicioweb.dto.AppointmentResponseDTO;
import com.sigepres.servicioweb.service.AppointmentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    private final AppointmentServiceImpl appointmentService;

    public AppointmentController(AppointmentServiceImpl appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping()
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return new ResponseEntity<>(appointmentService.createAppointment(appointmentRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointment(@PathVariable("id") Integer appointmentId){
        return new ResponseEntity<>(appointmentService.getAppointmentById(appointmentId), HttpStatus.OK);
    }

    @GetMapping("/appointments/employee/{id}")
    public  ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByEmployeeId(
            @PathVariable("id") Integer employeeId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        if (startDate == null || endDate == null){
            return new ResponseEntity<>(appointmentService.getAppointmentsByEmployeeId(employeeId), HttpStatus.OK);
        }
        return new ResponseEntity<>(appointmentService.getAppointmentsByEmployeeAndDates(employeeId, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/appointments/customer/{id}")
    public  ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByCustomerId(
            @PathVariable("id") Integer customerId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        if (startDate == null || endDate == null){
            return new ResponseEntity<>(appointmentService.getAppointmentsByCustomerId(customerId), HttpStatus.OK);
        }
        return new ResponseEntity<>(appointmentService.getAppointmentsByCustomerAndDates(customerId, startDate, endDate), HttpStatus.OK);

    }

    @GetMapping("/appointments")
    public  ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsBetweenDates(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        System.out.println("startDate = " + startDate);
        System.out.println("endDate = " + endDate);

        if (startDate == null || endDate == null){
            // Lógica cuando no se proporcionan fechas, devuelve todas las citas.
            return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
        }
        // // Manejar el caso cuando se proporcionan ambas fechas.
        return new ResponseEntity<>(appointmentService.getAppointmentsBetweenDates(startDate, endDate), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(@PathVariable("id") Integer appointmentId,                                            @Valid @RequestBody AppointmentRequestDTO appointmentRequestDTO){
        return new ResponseEntity<>(appointmentService.updateAppointment(appointmentId, appointmentRequestDTO), HttpStatus.OK);
    }
}
