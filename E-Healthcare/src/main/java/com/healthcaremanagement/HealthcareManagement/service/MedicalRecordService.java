package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.MedicalRecordDTO;
import com.healthcaremanagement.HealthcareManagement.entity.MedicalRecordEntity;
import com.healthcaremanagement.HealthcareManagement.repository.MedicalRecordRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    // Convert entity to DTO
    private MedicalRecordDTO convertEntityToDTO(MedicalRecordEntity medicalRecordEntity) {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        BeanUtils.copyProperties(medicalRecordEntity, medicalRecordDTO);
        return medicalRecordDTO;
    }

    // Convert DTO to entity
    private MedicalRecordEntity convertDTOToEntity(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        BeanUtils.copyProperties(medicalRecordDTO, medicalRecordEntity);
        return medicalRecordEntity;
    }

    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordEntity medicalRecordEntity = convertDTOToEntity(medicalRecordDTO);
        MedicalRecordEntity savedMedicalRecord = medicalRecordRepository.save(medicalRecordEntity);
        return convertEntityToDTO(savedMedicalRecord);
    }

    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return medicalRecordRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public MedicalRecordDTO getMedicalRecordById(Long medicalRecordId) {
        return medicalRecordRepository.findById(medicalRecordId).map(this::convertEntityToDTO).orElse(null);
    }

    public MedicalRecordDTO updateMedicalRecord(Long medicalRecordId, MedicalRecordDTO medicalRecordDTO) {
        if (medicalRecordRepository.existsById(medicalRecordId)) {
            MedicalRecordEntity medicalRecordEntity = convertDTOToEntity(medicalRecordDTO);
            medicalRecordEntity.setMedicalRecordId(medicalRecordId);
            MedicalRecordEntity updatedMedicalRecord = medicalRecordRepository.save(medicalRecordEntity);
            return convertEntityToDTO(updatedMedicalRecord);
        }
        return null;
    }

    public void deleteMedicalRecord(Long medicalRecordId) {
        medicalRecordRepository.deleteById(medicalRecordId);
    }
}

