package com.healthcaremanagement.HealthcareManagement.dto;

import com.healthcaremanagement.HealthcareManagement.entity.PatientEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDTO {
    private Long medicalRecordId;

    @NotEmpty(message = "This field should not be empty")
    private Long patientId;

    @NotEmpty(message = "This field should not be empty")
    private String record;

}
