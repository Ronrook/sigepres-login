package com.sigepres.servicioweb.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordRequestDTO {

    @NotNull(message = "El ID del cliente no puede ser nulo.")
    private Integer customerId;

    private String diagnosis;

    private String legalGuardian;

    private boolean historyIsActive;
}
