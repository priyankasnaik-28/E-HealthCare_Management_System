package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.MedicalRecordDTO;
import com.healthcaremanagement.HealthcareManagement.dto.PatientDTO;
import com.healthcaremanagement.HealthcareManagement.entity.MedicalRecordEntity;
import com.healthcaremanagement.HealthcareManagement.entity.PatientEntity;
import com.healthcaremanagement.HealthcareManagement.exception.PatientNotFoundException;
import com.healthcaremanagement.HealthcareManagement.exception.SaveErrorException;
import com.healthcaremanagement.HealthcareManagement.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signUp_Success() throws SaveErrorException {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("test@example.com");
        patientDTO.setPhone("9876543210");
        patientDTO.setPassword("password");

        PatientEntity patientEntity = new PatientEntity();
        BeanUtils.copyProperties(patientDTO, patientEntity);

        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);

        PatientDTO result = patientService.signUp(patientDTO);

        assertEquals(patientDTO.getEmail(), result.getEmail());
        assertEquals(patientDTO.getPhone(), result.getPhone());
    }

    @Test
    public void signUp_Fail() throws SaveErrorException {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("test@example.com");
        patientDTO.setPhone("9876543210");
        patientDTO.setPassword("password");

        when(patientRepository.save(any(PatientEntity.class))).thenThrow(new RuntimeException("The Email and PhoneNumber already Exist!"));

        assertThrows(SaveErrorException.class, () -> patientService.signUp(patientDTO));
    }

    @Test
    public void login_Success() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("test@example.com");
        patientDTO.setPassword("password");

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setEmail("test@example.com");
        patientEntity.setPassword("password");

        when(patientRepository.findByEmail(anyString())).thenReturn(Optional.of(patientEntity));

        boolean result = patientService.login(patientDTO);

        assertTrue(result);
    }

    @Test
    public void login_Fail() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("test@example.com");
        patientDTO.setPassword("password");

        when(patientRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        boolean result = patientService.login(patientDTO);

        assertFalse(result);
    }

    @Test
    public void getAllPatients_Success() {
        List<PatientEntity> patientEntities = Arrays.asList(new PatientEntity(), new PatientEntity());

        when(patientRepository.findAll()).thenReturn(patientEntities);

        List<PatientDTO> result = patientService.getAllPatients();

        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    public void getPatientById_Success() throws PatientNotFoundException {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setPatientId(1L);

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patientEntity));

        PatientDTO result = patientService.getPatientById(1L);

        assertEquals(patientEntity.getPatientId(), 1);

    }

    @Test
    public void getPatientById_Fail() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(1L));
    }

    @Test
    public void updatePatient_Success() {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName("John");
        patientDTO.setLastName("Doe");

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setPatientId(1L);
        patientEntity.setFirstName("Jane");
        patientEntity.setLastName("Smith");

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patientEntity));
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);

        PatientDTO result = patientService.updatePatient(1L, patientDTO);

        assertEquals(patientDTO.getFirstName(), result.getFirstName());
        assertEquals(patientDTO.getLastName(), result.getLastName());
    }

    @Test
    public void updatePatient_Fail() {
        PatientDTO patientDTO = new PatientDTO();

        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> patientService.updatePatient(1L, patientDTO));
    }

    @Test
    public void deletePatient_Success() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(new PatientEntity()));

        patientService.deletePatient(1L);

        verify(patientRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deletePatient_Fail() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> patientService.deletePatient(1L));
    }
}
