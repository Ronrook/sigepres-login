package com.sigepres.servicioweb.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseDTO {

    private int noteId;

    private LocalDate noteDate;

    private String observations;

    private String employeeFullName;

    private Integer medicalRecordId;
}
