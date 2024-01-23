package com.sigepres.servicioweb.repository;


import com.sigepres.servicioweb.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface INoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByMedicalRecordId(Integer medicalHistoryId);
}
