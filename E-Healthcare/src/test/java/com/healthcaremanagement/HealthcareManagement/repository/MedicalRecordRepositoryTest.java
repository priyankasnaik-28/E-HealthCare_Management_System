package com.healthcaremanagement.HealthcareManagement.repository;

import com.healthcaremanagement.HealthcareManagement.entity.MedicalRecordEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MedicalRecordRepositoryTest {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    private MedicalRecordEntity medicalRecordEntity;

    @BeforeEach
    void setUp() {
        medicalRecordEntity = new MedicalRecordEntity();

        medicalRecordEntity.setMedicalRecordId(1L);
        medicalRecordEntity.setRecord("patient");

        medicalRecordRepository.save(medicalRecordEntity);
    }

    @Test
    void testFindById() {
        Optional<MedicalRecordEntity> foundMedicalRecordEntity = medicalRecordRepository.findById(medicalRecordEntity.getMedicalRecordId());
        assertTrue(foundMedicalRecordEntity.isPresent());
        assertEquals(medicalRecordEntity.getMedicalRecordId(), foundMedicalRecordEntity.get().getMedicalRecordId());
    }

    @Test
    void testFindById_NotFound() {
        Optional<MedicalRecordEntity> foundMedicalRecordEntity = medicalRecordRepository.findById(999L); // Assuming 999L does not exist
        assertFalse(foundMedicalRecordEntity.isPresent(), "Expected not to find a medical record with a non-existent ID");
    }

    @Test
    void testSaveMedicalRecordEntity() {
        MedicalRecordEntity newMedicalRecordEntity = new MedicalRecordEntity();

        newMedicalRecordEntity.setMedicalRecordId(2L);
        newMedicalRecordEntity.setRecord("patient1");

        MedicalRecordEntity savedMedicalRecordEntity = medicalRecordRepository.save(newMedicalRecordEntity);
        assertNotNull(savedMedicalRecordEntity.getMedicalRecordId());
        assertEquals("patient1", savedMedicalRecordEntity.getRecord());
    }

    @Test
    void testSaveMedicalRecordEntityWithMissingFields() {
        MedicalRecordEntity incompleteMedicalRecord = new MedicalRecordEntity();
        incompleteMedicalRecord.setMedicalRecordId(3L);
        // Missing the record field, which might be required depending on the database schema and application logic
        try{
            medicalRecordRepository.save(incompleteMedicalRecord);
        }catch (Exception e){
            throw new RuntimeException("Error saving data"+e.getMessage());
        }
       }


    @Test
    void testDeleteMedicalRecordEntity() {
        medicalRecordRepository.delete(medicalRecordEntity);
        Optional<MedicalRecordEntity> deletedMedicalRecordEntity = medicalRecordRepository.findById(medicalRecordEntity.getMedicalRecordId());
        assertFalse(deletedMedicalRecordEntity.isPresent());
    }

    @Test
    void testDeleteNonExistentMedicalRecordEntity() {
        try {
            medicalRecordRepository.deleteById(999L); // Assuming 999L does not exist
        } catch (Exception e) {
            fail("Expected no exception to be thrown when deleting a non-existent medical record");
        }
        // No exception should occur, as deleting a non-existent entity is usually a no-op in JPA
    }


    @Test
    void testFindAll() {
        Iterable<MedicalRecordEntity> adminEntities = medicalRecordRepository.findAll();
        assertNotNull(adminEntities);
        assertTrue(adminEntities.iterator().hasNext());
    }

    @Test
    void testFindAll_NoMedicalRecordsFound() {
        // Delete all medical records to ensure the repository is empty
        medicalRecordRepository.deleteAll();

        // Fetch all medical records from the repository
        Iterable<MedicalRecordEntity> medicalRecordEntities = medicalRecordRepository.findAll();

        // Check that the result is not null and contains no entities
        assertNotNull(medicalRecordEntities, "Expected non-null result from findAll()");
        assertFalse(medicalRecordEntities.iterator().hasNext(), "Expected no medical records in the repository");
    }


}
