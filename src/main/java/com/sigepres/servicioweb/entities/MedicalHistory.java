package com.sigepres.servicioweb.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "medical_history")
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_history_id")
    private int medicalHistoryId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
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

    @OneToMany(mappedBy = "medicalHistory", fetch = FetchType.EAGER)
    private List<Note> notes;

}