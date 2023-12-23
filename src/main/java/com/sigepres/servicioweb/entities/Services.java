package com.sigepres.servicioweb.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "service_name", nullable = false, length = 45, unique = true)
    private String serviceName;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "service_image")
    private Integer serviceImage;

    @Column(name = "service_price")
    private Double servicePrice;
    }