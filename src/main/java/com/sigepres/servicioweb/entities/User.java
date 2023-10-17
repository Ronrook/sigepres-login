package com.sigepres.servicioweb.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "second_last_name", length = 50)
    private String secondLastName;

    @Column(name = "dni_number", nullable = false, length = 20, unique = true)
    private String dniNumber;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "user_image")
    private Integer userImage;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToOne(mappedBy = "user")
    private Employee employee;

    @OneToOne(mappedBy = "user")
    private Customer customer;


    // Constructor vacío
    public User() {
    }

    public User(String firstName, String middleName, String lastName, String secondLastName, String dniNumber,
                LocalDate birthdate, String email, String phone, Integer userImage, boolean isActive,
                Employee employee, Customer customer) {

        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.dniNumber = dniNumber;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.userImage = userImage;
        this.isActive = isActive;
        this.employee = employee;
        this.customer = customer;
    }


}
