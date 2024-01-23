package com.sigepres.servicioweb.dto;



import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordResponseDTO {

    private Integer medicalRecordId;

    private CustomerDTO customer;

    private LocalDate creationDate;

    private LocalDate updateDate;

    private String diagnosis;

    private String legalGuardian;

    private boolean historyIsActive;

}
