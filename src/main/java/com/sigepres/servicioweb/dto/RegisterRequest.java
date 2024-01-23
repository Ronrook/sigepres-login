package com.sigepres.servicioweb.dto;




import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El primer nombre del usuario no debe ser nulo o vacío")
    @Size(max =50, message = "El primer nombre del usuario no debe ser mayor a 50 caracteres")
    private String firstName;

    @Size(min = 1, max =50, message = "El segundo nombre del usuario no debe ser mayor a 50 caracteres")
    private String middleName;

    @NotBlank(message = "El primer apellido del usuario no debe ser nulo o vacío")
    @Size(max =50, message = "El primer apellido del usuario no debe ser mayor a 50 caracteres")
    private String lastName;

    @Size(min = 1, max =50, message = "El segundo apellido del usuario no debe ser mayor a 50 caracteres")
    private String secondLastName;

    @NotBlank(message = "El dni del usuario no debe ser nulo o vacío")
    @Size(min = 5, max =20, message = "El dni del usuario no debe ser mayor a 20 caracteres")
    private String dniNumber;

    @Past
    @NotNull(message = "La fecha no debe ser nula")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthdate;

    @NotBlank(message = "El Email del usuario no debe ser nulo o vacío")
    @Email(message = "El email debe ser valido")
    private String email;

    @NotNull(message = "El teléfono del usuario no debe ser nulo o vacío")
    @Size(min = 10, max =15, message = "El telefono del usuario no debe ser mayor a 15 caracteres")
    private String phone;

    private Integer userImage;

    @AssertTrue
    private Boolean isActive;

    private String password;

    private String role;
}
