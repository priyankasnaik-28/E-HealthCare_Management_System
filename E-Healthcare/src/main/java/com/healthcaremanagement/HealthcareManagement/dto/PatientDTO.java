package com.healthcaremanagement.HealthcareManagement.dto;

import com.healthcaremanagement.HealthcareManagement.entity.AppointmentEntity;
import com.healthcaremanagement.HealthcareManagement.entity.BillingEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long patientId;

    @NotEmpty(message = "First Name should not be null")
    private String firstName;

    @NotNull(message = "Last Name should not be null")
    private String lastName;

    @NotNull(message = "Date of Birth Name should not be null")
    private Date dob;

    @NotNull(message = "Gender should not be null")
    private String gender;

    @NotNull(message = "Address should not be null")
    private String address;

    @Email(message = "Email should not be null")
    private String email;

    @Size(min = 6, message = "Password should not be less than 6 characters")
    private String password;

    @Column(unique = true)
    private String phone;


    private List<AppointmentEntity> appointments;
    private List<BillingEntity> billings;

}
