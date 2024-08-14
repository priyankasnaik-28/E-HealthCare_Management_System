package com.healthcaremanagement.HealthcareManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    private String firstName;
    private String lastName;
    private Date dob;
    private String gender;
    private String address;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;


    private String password;

    @OneToMany(mappedBy = "patient")
    private List<AppointmentEntity> appointments;

    @OneToMany(mappedBy = "patient")
    private List<BillingEntity> billings;


}