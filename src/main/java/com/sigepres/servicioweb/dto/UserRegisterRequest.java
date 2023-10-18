package com.sigepres.servicioweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotNull(message = "El primer nombre del usuario no debe ser nulo")
    @Size(min = 1, max =50, message = "El primer nombre del usuario no debe ser mayor a 50 caracteres")
    private String firstName;

    @Size(min = 1, max =50, message = "El segundo nombre del usuario no debe ser mayor a 50 caracteres")
    private String middleName;


    @NotNull(message = "El primer apellido del usuario no debe ser nulo")
    @Size(min = 1, max =50, message = "El primer apellido del usuario no debe ser mayor a 50 caracteres")
    private String lastName;

    @Size(min = 1, max =50, message = "El segundo apellido del usuario no debe ser mayor a 50 caracteres")
    private String secondLastName;

    @NotEmpty(message = "El dni del usuario no debe ser nulo o vacio")
    @Size(min = 5, max =20, message = "El dni del usuario no debe ser mayor a 20 caracteres")
    private String dniNumber;

    @Past
    private LocalDate birthdate;


    @NotNull(message = "El Email del usuario no debe ser nulo")
    @Email(message = "El email debe ser valido")
    private String email;
    @NotNull(message = "El telefono del usuario no debe ser nulo")
    @Size(min = 10, max =15, message = "El telefono del usuario no debe ser mayor a 15 caracteres")
    private String phone;

    @Min(value = 0)
    @Max(value = 1)
    private Integer userImage;

    @AssertTrue
    private boolean isActive;
}
