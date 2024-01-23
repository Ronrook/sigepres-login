package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.dto.NoteRequestDTO;
import com.sigepres.servicioweb.dto.NoteResponseDTO;
import com.sigepres.servicioweb.entities.Employee;
import com.sigepres.servicioweb.entities.MedicalRecord;
import com.sigepres.servicioweb.entities.Note;
import com.sigepres.servicioweb.exceptions.ResourceNotFoundException;
import com.sigepres.servicioweb.repository.INoteRepository;
import com.sigepres.servicioweb.service.interfaces.IEmployeeService;
import com.sigepres.servicioweb.service.interfaces.IMedicalRecordService;
import com.sigepres.servicioweb.service.interfaces.INoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoteServiceImpl implements INoteService {

    private final INoteRepository noteRepository;

    private final IEmployeeService employeeService;

    private final IMedicalRecordService medicalHistoryService;

    @Autowired
    private final ModelMapper modelMapper;

    public NoteServiceImpl(INoteRepository noteRepository, IEmployeeService employeeService, IMedicalRecordService medicalHistoryService, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.employeeService = employeeService;
        this.medicalHistoryService = medicalHistoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public NoteResponseDTO saveNote(NoteRequestDTO noteRequest) {

        Employee employee =  employeeService.getEmployeeEntityById(noteRequest.getEmployeeId());

        MedicalRecord medicalRecord = medicalHistoryService.getMedicalRecordEntityById(noteRequest.getMedicalRecordId());

        //Note note = modelMapper.map(noteRequest, Note.class);
        Note note = mapToNote(noteRequest, employee, medicalRecord);

        noteRepository.save(note);

        return modelMapper.map(note, NoteResponseDTO.class);
    }
    private Note mapToNote(NoteRequestDTO noteRequest, Employee employee,  MedicalRecord medicalRecord){
        Note note = modelMapper.map(noteRequest, Note.class);
        note.setEmployeeCreator(employee);
        note.setNoteDate(LocalDate.now());
        note.setMedicalRecord(medicalRecord);
        return note;
    }

    @Override
    public NoteResponseDTO getNoteById(Integer noteId) {
        Note note =  noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Nota no encontrada."));
        return modelMapper.map(note, NoteResponseDTO.class);
    }

    @Override
    public List<NoteResponseDTO> getNotesByMedicalRecord(Integer medicalRecordId) {

        medicalHistoryService.getMedicalRecordEntityById(medicalRecordId);

        List<Note> appointments = noteRepository.findByMedicalRecordId(medicalRecordId);

        return appointments.stream()
                .map(note -> modelMapper.map(note, NoteResponseDTO.class))
                .toList();
    }

    @Override
    public void deleteNoteById(int noteId) {

    }
}
