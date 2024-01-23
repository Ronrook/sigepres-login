package com.sigepres.servicioweb.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medical_history")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_history_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "diagnosis", nullable = false, columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "legal_guardian", length = 100)
    private String legalGuardian;

    @Column(name = "history_is_active", nullable = false)
    private boolean historyIsActive;

    @OneToMany(mappedBy = "medicalRecord", fetch = FetchType.EAGER)
    private List<Note> notes;

}