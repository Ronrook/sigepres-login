package com.sigepres.servicioweb.controller;

import com.sigepres.servicioweb.dto.NoteRequestDTO;
import com.sigepres.servicioweb.dto.NoteResponseDTO;
import com.sigepres.servicioweb.service.interfaces.INoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    INoteService noteService;

    public NoteController(INoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping()
    public ResponseEntity<NoteResponseDTO> createNote(@Valid @RequestBody NoteRequestDTO noteRequest) {
        return new ResponseEntity<>(noteService.saveNote(noteRequest), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponseDTO> getNoteById(@PathVariable("id")Integer noteId){
        return new ResponseEntity<>(noteService.getNoteById(noteId), HttpStatus.OK);
    }

    @GetMapping("medical_record/{id}")
    public ResponseEntity<List<NoteResponseDTO>> getNotesByMedicalRecord(@PathVariable("id") Integer medicalHistoryId){
        return new ResponseEntity<>(noteService.getNotesByMedicalRecord(medicalHistoryId), HttpStatus.OK);
    }
}
