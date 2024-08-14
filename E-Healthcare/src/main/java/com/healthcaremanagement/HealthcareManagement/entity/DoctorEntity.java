package com.healthcaremanagement.HealthcareManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    private String firstName;
    private String lastName;
    private String specialization;
    private String schedule;

    @OneToMany(mappedBy = "doctor")
    private List<AppointmentEntity> appointments;
}
