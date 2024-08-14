package com.healthcaremanagement.HealthcareManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private Long doctorId;

    @NotNull(message = "First Name should not be null")
    private String firstName;

    @NotNull(message = "First Name should not be null")
    private String lastName;

    @NotNull(message = "First Name should not be null")
    private String specialization;

    @NotNull(message = "First Name should not be null")
    private String schedule;
}
