package com.healthcaremanagement.HealthcareManagement.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;

@Entity
@Data
public class BillingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    private Double amount;
    private Date billingDate;
    private String paymentStatus;

}
