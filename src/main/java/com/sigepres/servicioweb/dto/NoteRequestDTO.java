package com.sigepres.servicioweb.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequestDTO {

    private String observations;

    private Integer employeeId;

    private Integer medicalRecordId;
}
