package com.healthcaremanagement.HealthcareManagement.entity;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    public class AdminEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long adminId;
        private String name;
        private String email;
        private String password;
    }
