package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.MedicalRecordDTO;
import com.healthcaremanagement.HealthcareManagement.dto.PatientDTO;
import com.healthcaremanagement.HealthcareManagement.exception.PatientNotFoundException;
import com.healthcaremanagement.HealthcareManagement.exception.SaveErrorException;
import com.healthcaremanagement.HealthcareManagement.service.MedicalRecordService;
import com.healthcaremanagement.HealthcareManagement.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signUpSuccess() throws SaveErrorException {
        PatientDTO patientDTO = new PatientDTO();
        when(patientService.signUp(any(PatientDTO.class))).thenReturn(patientDTO);

        ResponseEntity<?> response = patientController.signUp(patientDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).signUp(patientDTO);
    }

    @Test
    public void signUpFail() throws SaveErrorException {
        PatientDTO patientDTO = new PatientDTO();
        doThrow(new SaveErrorException("Error saving patient")).when(patientService).signUp(any(PatientDTO.class));

        ResponseEntity<?> response = patientController.signUp(patientDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error saving patient", response.getBody());
        verify(patientService, times(1)).signUp(patientDTO);
    }

    @Test
    public void loginSuccess() {
        PatientDTO patientDTO = new PatientDTO();
        when(patientService.login(any(PatientDTO.class))).thenReturn(true);

        ResponseEntity<String> response = patientController.login(patientDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful!", response.getBody());
        verify(patientService, times(1)).login(patientDTO);
    }

    @Test
    public void loginFail() {
        PatientDTO patientDTO = new PatientDTO();
        when(patientService.login(any(PatientDTO.class))).thenReturn(false);

        ResponseEntity<String> response = patientController.login(patientDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody());
        verify(patientService, times(1)).login(patientDTO);
    }

    @Test
    public void getAllPatientsSuccess() {
        List<PatientDTO> patientList = new ArrayList<>();
        when(patientService.getAllPatients()).thenReturn(patientList);

        List<PatientDTO> response = patientController.getAllPatients();

        assertEquals(patientList, response);
        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    public void getPatientByIdSuccess() throws PatientNotFoundException {
        PatientDTO patientDTO = new PatientDTO();
        when(patientService.getPatientById(anyLong())).thenReturn(patientDTO);

        ResponseEntity<PatientDTO> response = patientController.getPatientById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).getPatientById(1L);
    }

    @Test
    public void getPatientByIdFail() throws PatientNotFoundException {
        when(patientService.getPatientById(anyLong())).thenThrow(new PatientNotFoundException("Patient not found"));

        ResponseEntity<PatientDTO> response = patientController.getPatientById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(patientService, times(1)).getPatientById(1L);
    }


    @Test
    public void updatePatientSuccess() {
        PatientDTO patientDTO = new PatientDTO();
        when(patientService.updatePatient(anyLong(), any(PatientDTO.class))).thenReturn(patientDTO);

        ResponseEntity<PatientDTO> response = patientController.updatePatient(1L, patientDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(patientDTO, response.getBody());
        verify(patientService, times(1)).updatePatient(1L, patientDTO);
    }

    @Test
    public void updatePatientFail() {
        PatientDTO patientDTO = new PatientDTO();
        when(patientService.updatePatient(anyLong(), any(PatientDTO.class))).thenReturn(null);

        ResponseEntity<PatientDTO> response = patientController.updatePatient(1L, patientDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(patientService, times(1)).updatePatient(1L, patientDTO);
    }

    @Test
    public void deletePatientSuccess() {
        doNothing().when(patientService).deletePatient(anyLong());

        ResponseEntity<String> response = patientController.deletePatient(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
        verify(patientService, times(1)).deletePatient(1L);
    }

    @Test
    public void deletePatientFail() {
        doThrow(new RuntimeException("Patient not found")).when(patientService).deletePatient(anyLong());

        ResponseEntity<String> response = patientController.deletePatient(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Patient not found", response.getBody());
        verify(patientService, times(1)).deletePatient(1L);
    }



}
