package com.sigepres.servicioweb.controller;

import com.sigepres.servicioweb.dto.AppointmentRequestDTO;
import com.sigepres.servicioweb.dto.AppointmentResponseDTO;
import com.sigepres.servicioweb.service.AppointmentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/appointments/{id}")
    public  ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByEmployeeId(@PathVariable("id") Integer employeeId) {
        return new ResponseEntity<>(appointmentService.getAppointmentsByEmployeeId(employeeId), HttpStatus.OK);
    }
}
