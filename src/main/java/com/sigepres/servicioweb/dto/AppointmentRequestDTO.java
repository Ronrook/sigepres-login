package com.sigepres.servicioweb.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.sql.Time;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {

    private Integer appointmentId;

    @NotNull(message = "La fecha de la cita no puede ser nula.")
    @Future(message = "La fecha de la cita debe ser en el futuro.")
    private LocalDate appointmentDate;

    @NotNull(message = "La hora de inicio de la cita no puede ser nula.")
    private Time appointmentStartTime;

    @NotNull(message = "La hora final de la cita no puede ser nula.")
    private Time appointmentEndTime;

    @NotNull(message = "El ID del cliente no puede ser nulo.")
    private Integer customerId;

    @NotNull(message = "El ID del servicio no puede ser nulo.")
    private Integer serviceId;

    @NotNull(message = "El ID del empleado no puede ser nulo.")
    private Integer employeeId;

    private boolean isAttended;
}
