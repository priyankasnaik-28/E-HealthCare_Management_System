package com.healthcaremanagement.HealthcareManagement.repository;

import com.healthcaremanagement.HealthcareManagement.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity,Long> {
}
