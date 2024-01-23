package com.sigepres.servicioweb.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private  String customerFullName;

    private String dniNumber;

    private LocalDate birthdate;
}
