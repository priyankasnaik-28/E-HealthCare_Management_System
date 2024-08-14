package com.healthcaremanagement.HealthcareManagement.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    private Long id;

    @NotEmpty(message = "This field should not be empty")
    private String name;

    @NotEmpty(message = "This field should not be empty")
    private String email;

    @NotEmpty(message = "This field should not be empty")
    private String password;
}
