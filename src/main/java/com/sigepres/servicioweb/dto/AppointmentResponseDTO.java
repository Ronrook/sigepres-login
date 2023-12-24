package com.sigepres.servicioweb.dto;


import lombok.*;


import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {


    private LocalDate appointmentDate;

    private Time appointmentStartTime;

    private Time appointmentEndTime;

    private Integer customerId;

    private Integer serviceId;

    private Integer employeeId;
}
