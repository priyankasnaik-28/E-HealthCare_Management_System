package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.MedicalRecordDTO;
import com.healthcaremanagement.HealthcareManagement.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicalRecords")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("/create")
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecordDTO) {
        return ResponseEntity.ok(medicalRecordService.createMedicalRecord(medicalRecordDTO));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<MedicalRecordDTO>> getAllMedicalRecords() {
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecordById(@PathVariable Long id) {
        MedicalRecordDTO medicalRecordDTO = medicalRecordService.getMedicalRecordById(id);
        if (medicalRecordDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(medicalRecordDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecordDTO medicalRecordDTO) {
        MedicalRecordDTO updatedDTO = medicalRecordService.updateMedicalRecord(id, medicalRecordDTO);
        if (updatedDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
        try {
            medicalRecordService.deleteMedicalRecord(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

