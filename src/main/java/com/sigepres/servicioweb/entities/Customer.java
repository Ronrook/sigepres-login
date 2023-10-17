package com.sigepres.servicioweb.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    @Id
    @Column(name = "user_id")
    private Integer customerId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor vacío
    public Customer() {
    }
}
