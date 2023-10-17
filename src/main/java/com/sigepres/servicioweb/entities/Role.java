package com.sigepres.servicioweb.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private byte roleId;

    @Column(name = "role_name", nullable = false, length = 45, unique = true)
    private String roleName;


    public Role() {
    }

}
