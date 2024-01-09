package com.sigepres.servicioweb.repository;

import com.sigepres.servicioweb.entities.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
}
