package com.healthcaremanagement.HealthcareManagement.repository;

import com.healthcaremanagement.HealthcareManagement.entity.PatientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    public void setup() throws ParseException {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setPatientId(1L);
        patientEntity.setFirstName("person1");
        patientEntity.setLastName("last");
        patientEntity.setDob(parseDate("2001-01-01"));
        patientEntity.setGender("Male");
        patientEntity.setAddress("123 Street");
        patientEntity.setEmail("person1@example.com");
        patientEntity.setPhone("9076543210");
        patientEntity.setPassword("password");

        patientRepository.save(patientEntity);
    }

    @Test
    public void findByEmail_Success() throws ParseException {
        // Given
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setPatientId(2L);
        patientEntity.setFirstName("person2");
        patientEntity.setLastName("last");
        patientEntity.setDob(parseDate("2001-01-01"));
        patientEntity.setGender("Male");
        patientEntity.setAddress("123 Street");
        patientEntity.setEmail("person2@example.com");
        patientEntity.setPhone("8796009879");
        patientEntity.setPassword("password");

        patientRepository.save(patientEntity);

        // When
        Optional<PatientEntity> foundPatient = patientRepository.findByEmail("person2@example.com");

        // Then
        assertTrue(foundPatient.isPresent());
        assertEquals("person2@example.com", foundPatient.get().getEmail());
    }

    @Test
    public void findByEmail_NotFound() {
        // When
        Optional<PatientEntity> foundPatient = patientRepository.findByEmail("nonexistent@example.com");

        // Then
        assertTrue(foundPatient.isEmpty());
    }

    @Test
    public void savePatient_Success() {
        // Given
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("Jane");
        patientEntity.setLastName("Doe");
        patientEntity.setDob(new Date());
        patientEntity.setGender("Female");
        patientEntity.setAddress("456 Avenue");
        patientEntity.setEmail("jane.doe@example.com");
        patientEntity.setPhone("1234567890");
        patientEntity.setPassword("password");

        // When
        PatientEntity savedPatient = patientRepository.save(patientEntity);

        // Then
        assertEquals(patientEntity.getEmail(), savedPatient.getEmail());
    }

    @Test
    public void savePatient_Failure_DuplicateEmail() {
        // Given
        PatientEntity patientEntity1 = new PatientEntity();
        patientEntity1.setFirstName("John");
        patientEntity1.setLastName("Doe");
        patientEntity1.setDob(new Date());
        patientEntity1.setGender("Male");
        patientEntity1.setAddress("123 Street");
        patientEntity1.setEmail("duplicate@example.com");
        patientEntity1.setPhone("1234567890");
        patientEntity1.setPassword("password");

        patientRepository.save(patientEntity1);

        PatientEntity patientEntity2 = new PatientEntity();
        patientEntity2.setFirstName("Jane");
        patientEntity2.setLastName("Doe");
        patientEntity2.setDob(new Date());
        patientEntity2.setGender("Female");
        patientEntity2.setAddress("456 Avenue");
        patientEntity2.setEmail("duplicate@example.com");
        patientEntity2.setPhone("0987654321");
        patientEntity2.setPassword("password");

        // When & Then
        assertThrows(Exception.class, () -> {
            patientRepository.save(patientEntity2);
        });
    }


    @Test
    public void findById_Failure_NonExistentId() {

        Long nonExistentId = 999L;

        Optional<PatientEntity> foundPatient = patientRepository.findById(nonExistentId);

        assertTrue(foundPatient.isEmpty());
    }


    @Test
    public void deletePatient_Success() {
        // Given
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName("Alice");
        patientEntity.setLastName("Smith");
        patientEntity.setDob(new Date());
        patientEntity.setGender("Female");
        patientEntity.setAddress("789 Boulevard");
        patientEntity.setEmail("alice.smith@example.com");
        patientEntity.setPhone("5555555555");
        patientEntity.setPassword("password");

        patientEntity = patientRepository.save(patientEntity);

        // When
        patientRepository.deleteById(patientEntity.getPatientId());

        // Then
        Optional<PatientEntity> deletedPatient = patientRepository.findById(patientEntity.getPatientId());
        assertTrue(deletedPatient.isEmpty());
    }

    @Test
    public void deletePatient_Failure_NonExistentId() {
        // Given
        Long nonExistentId = 999L;

        try{
            patientRepository.deleteById(nonExistentId);
        }catch (Exception e){
            throw new RuntimeException("Error message"+e.getMessage());
        }
    }


    public static Date parseDate(String dateString) throws ParseException {
        // Define the expected date format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Parse and return the Date
        return formatter.parse(dateString);
    }

}
