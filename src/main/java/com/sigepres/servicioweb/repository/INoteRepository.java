package com.sigepres.servicioweb.repository;

import com.sigepres.servicioweb.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INoteRepository extends JpaRepository<Note, Integer> {
}
