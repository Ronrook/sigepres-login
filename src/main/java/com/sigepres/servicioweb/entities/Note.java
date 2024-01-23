package com.sigepres.servicioweb.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int noteId;

    @Column(name = "note_date", nullable = false)
    private LocalDate noteDate;

    @Column(name = "observations", nullable = false, columnDefinition = "TEXT")
    private String observations;

    @ManyToOne
    @JoinColumn(name = "employee_creator",  referencedColumnName = "employee_id", nullable = false)
    private Employee employeeCreator;

    @ManyToOne
    @JoinColumn(name = "history_number", nullable = false)
    private MedicalRecord medicalRecord;

}