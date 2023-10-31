package com.sigepres.servicioweb.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "customer_id")
@Table(name = "customers")
public class Customer extends User implements Serializable {
    // Atributos propios de la clase


}
