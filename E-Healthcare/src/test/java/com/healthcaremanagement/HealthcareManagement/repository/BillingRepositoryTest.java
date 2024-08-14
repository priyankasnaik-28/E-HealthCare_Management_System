package com.healthcaremanagement.HealthcareManagement.repository;

import com.healthcaremanagement.HealthcareManagement.entity.BillingEntity;
import com.healthcaremanagement.HealthcareManagement.entity.PatientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BillingRepositoryTest {

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private PatientRepository patientRepository;

    private PatientEntity patient;

    @BeforeEach
    public void setup() {
        // Setting up a patient entity
        patient = new PatientEntity();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setDob(new Date());
        patient.setGender("Male");
        patient.setAddress("123 Street");
        patient.setEmail("john.doe@example.com");
        patient.setPhone("9876543210");
        patient.setPassword("password");

        patient = patientRepository.save(patient);
    }

    // Success Test Cases

    @Test
    public void saveBilling_Success() {
        // Given
        BillingEntity billingEntity = new BillingEntity();
        billingEntity.setPatient(patient);
        billingEntity.setAmount(100.0);
        billingEntity.setBillingDate(new Date());
        billingEntity.setPaymentStatus("PAID");

        // When
        BillingEntity savedBilling = billingRepository.save(billingEntity);

        // Then
        assertNotNull(savedBilling.getBillingId());
        assertEquals(billingEntity.getAmount(), savedBilling.getAmount());
    }

    @Test
    public void findBillingById_Success() {
        // Given
        BillingEntity billingEntity = new BillingEntity();
        billingEntity.setPatient(patient);
        billingEntity.setAmount(200.0);
        billingEntity.setBillingDate(new Date());
        billingEntity.setPaymentStatus("UNPAID");

        BillingEntity savedBilling = billingRepository.save(billingEntity);

        // When
        Optional<BillingEntity> foundBilling = billingRepository.findById(savedBilling.getBillingId());

        // Then
        assertTrue(foundBilling.isPresent());
        assertEquals(savedBilling.getBillingId(), foundBilling.get().getBillingId());
    }

    @Test
    public void deleteBilling_Success() {
        // Given
        BillingEntity billingEntity = new BillingEntity();
        billingEntity.setPatient(patient);
        billingEntity.setAmount(150.0);
        billingEntity.setBillingDate(new Date());
        billingEntity.setPaymentStatus("PAID");

        billingEntity = billingRepository.save(billingEntity);

        // When
        billingRepository.deleteById(billingEntity.getBillingId());

        // Then
        Optional<BillingEntity> deletedBilling = billingRepository.findById(billingEntity.getBillingId());
        assertTrue(deletedBilling.isEmpty());
    }

    // Failure Test Cases

    @Test
    public void findBillingById_Failure_NonExistentId() {
        // Given
        Long nonExistentId = 999L;

        // When
        Optional<BillingEntity> foundBilling = billingRepository.findById(nonExistentId);

        // Then
        assertTrue(foundBilling.isEmpty());
    }

    @Test
    public void saveBilling_Failure_NullFields() {

        BillingEntity billingEntity = new BillingEntity();
        try {
            billingRepository.save(billingEntity);
        }catch (Exception e){
            throw new RuntimeException("Error exception"+e.getMessage());
        }

    }

    @Test
    public void deleteBilling_Failure_NonExistentId() {

        Long nonExistentId = 999L;

        try {
            billingRepository.deleteById(nonExistentId);

        } catch (Exception e) {
            throw new RuntimeException("Error message" + e.getMessage());
        }
    }
}
