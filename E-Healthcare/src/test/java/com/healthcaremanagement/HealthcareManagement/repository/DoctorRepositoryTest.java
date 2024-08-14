package com.healthcaremanagement.HealthcareManagement.repository;

import com.healthcaremanagement.HealthcareManagement.entity.DoctorEntity;
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
public class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    private DoctorEntity doctorEntity;

    @BeforeEach
    void setUp() {
        doctorEntity = new DoctorEntity();

        doctorEntity.setFirstName("first");
        doctorEntity.setLastName("last");
        doctorEntity.setSchedule("morning");
        doctorEntity.setSpecialization("dentist");

        doctorRepository.save(doctorEntity);
    }

    @Test
    void testFindById() {
        Optional<DoctorEntity> foundDoctorEntity = doctorRepository.findById(doctorEntity.getDoctorId());
        assertTrue(foundDoctorEntity.isPresent());
        assertEquals(doctorEntity.getDoctorId(), foundDoctorEntity.get().getDoctorId());
    }

    @Test
    void testFindById_NotFound() {
        Optional<DoctorEntity> foundDoctorEntity = doctorRepository.findById(999L); // Assuming 999L is a non-existent ID
        assertFalse(foundDoctorEntity.isPresent(), "Expected not to find a doctor with non-existent ID");
    }



    @Test
    void testSaveDoctorEntity() {
        DoctorEntity newDoctorEntity = new DoctorEntity();

        newDoctorEntity.setFirstName("FIRST");
        newDoctorEntity.setLastName("LAST");
        newDoctorEntity.setSchedule("MORNING");
        newDoctorEntity.setSpecialization("DENTIST");

        DoctorEntity savedDoctorEntity = doctorRepository.save(newDoctorEntity);
        assertNotNull(savedDoctorEntity.getDoctorId());
        assertEquals("FIRST", savedDoctorEntity.getFirstName());
    }

    @Test
    void testSaveDoctorEntityWithMissingFields() {
        DoctorEntity incompleteDoctor = new DoctorEntity();
        incompleteDoctor.setFirstName("OnlyFirstName"); // Missing last name, schedule, and specialization
        try {
            doctorRepository.save(incompleteDoctor);
        }catch (Exception e){
            throw new RuntimeException("cant save the data"+e.getMessage());
        }
    }

    @Test
    void testDeleteDoctorEntity() {
        doctorRepository.delete(doctorEntity);
        Optional<DoctorEntity> deletedDoctorEntity = doctorRepository.findById(doctorEntity.getDoctorId());
        assertFalse(deletedDoctorEntity.isPresent());
    }

    @Test
    void testDeleteNonExistentDoctorEntity() {
        try {
            doctorRepository.deleteById(999L); // Assuming 999L is a non-existent ID
        } catch (Exception e) {
            fail("Expected no exception to be thrown when deleting a non-existent doctor");
        }
        // No assertion needed since no exception should occur
    }

    @Test
    void testFindAll() {
        Iterable<DoctorEntity> adminEntities = doctorRepository.findAll();
        assertNotNull(adminEntities);
        assertTrue(adminEntities.iterator().hasNext());
    }
}
