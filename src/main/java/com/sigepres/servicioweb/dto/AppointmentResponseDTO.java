package com.sigepres.servicioweb.dto;


import lombok.*;


import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDTO {

    private Integer appointmentId;

    private LocalDate appointmentDate;

    private Time appointmentStartTime;

    private Time appointmentEndTime;

    private  String customerFullName;

    private  String employeeFullName;

    private String serviceName;

    private boolean isAttended;
}
