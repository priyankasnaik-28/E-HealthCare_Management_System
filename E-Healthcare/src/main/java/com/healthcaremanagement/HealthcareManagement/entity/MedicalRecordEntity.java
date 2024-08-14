package com.healthcaremanagement.HealthcareManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class MedicalRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicalRecordId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    private String record;

}
