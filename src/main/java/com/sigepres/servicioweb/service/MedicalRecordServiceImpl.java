package com.sigepres.servicioweb.service;

import com.sigepres.servicioweb.dto.MedicalRecordRequestDTO;
import com.sigepres.servicioweb.dto.MedicalRecordResponseDTO;
import com.sigepres.servicioweb.entities.*;
import com.sigepres.servicioweb.exceptions.DuplicateValueException;
import com.sigepres.servicioweb.exceptions.ResourceNotFoundException;
import com.sigepres.servicioweb.repository.IMedicalRecordRepository;
import com.sigepres.servicioweb.service.interfaces.ICustomerService;
import com.sigepres.servicioweb.service.interfaces.IMedicalRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MedicalRecordServiceImpl implements IMedicalRecordService {

    private final IMedicalRecordRepository medicalHistoryRepository;
    private final ICustomerService customerService;

    @Autowired
    private final ModelMapper modelMapper;

    public MedicalRecordServiceImpl(IMedicalRecordRepository medicalHistoryRepository, ICustomerService customerService, ModelMapper modelMapper) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public MedicalRecordResponseDTO createMedicalRecord(MedicalRecordRequestDTO medicalHistoryDTO) {

        // Validación que exista de Cliente
        Customer customer =  customerService.getCustomerById(medicalHistoryDTO.getCustomerId());

        // TODO Validar que no exista historia para mismo cliente
        if (medicalHistoryRepository.existsByCustomerId(medicalHistoryDTO.getCustomerId())) {
            throw new DuplicateValueException("Ya existe Historia clínica para el cliente con id: " + customer.getId());
        }

        MedicalRecord medicalRecord = mapToMedicalHistory(medicalHistoryDTO,customer);


        // Guardar la IMedicalRecordRepository en la base de datos
        medicalHistoryRepository.save(medicalRecord);

        return modelMapper.map(medicalRecord, MedicalRecordResponseDTO.class);
    }

    private MedicalRecord mapToMedicalHistory(MedicalRecordRequestDTO medicalHistoryDTO, Customer customer) {
        MedicalRecord medicalRecord = modelMapper.map(medicalHistoryDTO, MedicalRecord.class);
        medicalRecord.setCustomer(customer);
        medicalRecord.setCreationDate(LocalDate.now());
        medicalRecord.setUpdateDate(LocalDate.now());
        medicalRecord.setHistoryIsActive(true);
        return medicalRecord;
    }

    @Override
    public MedicalRecordResponseDTO getMedicalRecordById(Integer medicalHistoryId) {
        MedicalRecord medicalRecord = this.getMedicalRecordEntityById(medicalHistoryId);
        return modelMapper.map(medicalRecord, MedicalRecordResponseDTO.class);
    }

    @Override
    public MedicalRecord getMedicalRecordEntityById(Integer medicalHistoryId) {
        return medicalHistoryRepository.findById(medicalHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Historia médica no encontrada con id: " + medicalHistoryId));
    }

    @Override
    public MedicalRecordResponseDTO getMedicalRecordByCustomer(Integer customerId) {
        MedicalRecord medicalRecord = medicalHistoryRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Historia médica no encontrada con id del cliente"));
        return   modelMapper.map(medicalRecord, MedicalRecordResponseDTO.class);
    }


}
