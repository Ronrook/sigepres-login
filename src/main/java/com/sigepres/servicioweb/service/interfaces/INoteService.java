package com.sigepres.servicioweb.service.interfaces;


import com.sigepres.servicioweb.dto.NoteRequestDTO;
import com.sigepres.servicioweb.dto.NoteResponseDTO;


import java.util.List;

public interface INoteService {

    NoteResponseDTO saveNote(NoteRequestDTO noteRequest);

    NoteResponseDTO getNoteById(Integer noteId);

    List<NoteResponseDTO> getNotesByMedicalRecord(Integer medicalRecordId);

    void deleteNoteById(int noteId);

}
