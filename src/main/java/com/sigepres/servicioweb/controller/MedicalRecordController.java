package com.sigepres.servicioweb.controller;



import com.sigepres.servicioweb.dto.MedicalRecordRequestDTO;
import com.sigepres.servicioweb.dto.MedicalRecordResponseDTO;
import com.sigepres.servicioweb.service.interfaces.IMedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medical_records")
public class MedicalRecordController {

    IMedicalRecordService medicalRecordService;

    public MedicalRecordController(IMedicalRecordService medicalRecordService) {
        this.medicalRecordService =medicalRecordService;
    }

    @PostMapping()
    public ResponseEntity<MedicalRecordResponseDTO> createMedicalRecord(@Valid @RequestBody MedicalRecordRequestDTO medicalHistory){
        return new ResponseEntity<>(medicalRecordService.createMedicalRecord(medicalHistory), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDTO> getMedicalRecord(@PathVariable("id") Integer medicalRecordId){
        return new ResponseEntity<>(medicalRecordService.getMedicalRecordById(medicalRecordId), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<MedicalRecordResponseDTO> getMedicalRecordByCustomer(@PathVariable("id") Integer customerId){
        return new ResponseEntity<>(medicalRecordService.getMedicalRecordByCustomer(customerId), HttpStatus.OK);
    }
}
