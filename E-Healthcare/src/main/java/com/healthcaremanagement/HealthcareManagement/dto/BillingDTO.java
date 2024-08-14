package com.healthcaremanagement.HealthcareManagement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingDTO {

    private Long billingId;

    @NotNull(message = "First Name should not be null")
    private Long patientId;

    @NotNull(message = "First Name should not be null")
    private Double amount;

    @NotNull(message = "First Name should not be null")
    private Date billingDate;

    @NotNull(message = "First Name should not be null")
    private String paymentStatus;
}
