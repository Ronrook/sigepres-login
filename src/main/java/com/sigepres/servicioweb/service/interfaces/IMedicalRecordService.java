package com.sigepres.servicioweb.service.interfaces;

import com.sigepres.servicioweb.dto.MedicalRecordRequestDTO;
import com.sigepres.servicioweb.dto.MedicalRecordResponseDTO;
import com.sigepres.servicioweb.entities.MedicalRecord;


public interface IMedicalRecordService {

    MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO medicalHistory);

    MedicalRecordResponseDTO getMedicalRecordById(Integer medicalHistoryId);

    MedicalRecord getMedicalRecordEntityById(Integer medicalHistoryId);

    MedicalRecordResponseDTO getMedicalRecordByCustomer(Integer customerId);

}
