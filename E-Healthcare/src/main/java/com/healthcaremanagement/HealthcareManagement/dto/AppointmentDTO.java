package com.healthcaremanagement.HealthcareManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Long appointmentId;

    @NotNull(message = "First Name should not be null")
    private Long patientId;

    @NotNull(message = "First Name should not be null")
    private Long doctorId;

    @NotNull(message = "First Name should not be null")
    private Date appointmentDate;

    @NotNull(message = "First Name should not be null")
    private Time appointmentTime;

    @NotNull(message = "First Name should not be null")
    private String status;
}
