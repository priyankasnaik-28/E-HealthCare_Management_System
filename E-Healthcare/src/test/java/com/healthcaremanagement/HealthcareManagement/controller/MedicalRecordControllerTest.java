package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.MedicalRecordDTO;
import com.healthcaremanagement.HealthcareManagement.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createMedicalRecordSuccess() {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        when(medicalRecordService.createMedicalRecord(any(MedicalRecordDTO.class))).thenReturn(medicalRecordDTO);

        ResponseEntity<MedicalRecordDTO> response = medicalRecordController.createMedicalRecord(medicalRecordDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicalRecordDTO, response.getBody());
    }

    @Test
    public void getAllMedicalRecords() {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO();
        List<MedicalRecordDTO> list = Arrays.asList(medicalRecordDTO, medicalRecordDTO1);

        when(medicalRecordService.getAllMedicalRecords()).thenReturn(list);

        ResponseEntity<List<MedicalRecordDTO>> response = medicalRecordController.getAllMedicalRecords();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
        verify(medicalRecordService, times(1)).getAllMedicalRecords();
    }

    @Test
    public void getMedicalRecordByIdSuccess() {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        when(medicalRecordService.getMedicalRecordById(anyLong())).thenReturn(medicalRecordDTO);

        ResponseEntity<MedicalRecordDTO> response = medicalRecordController.getMedicalRecordById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(medicalRecordDTO, response.getBody());
        verify(medicalRecordService, times(1)).getMedicalRecordById(1L);
    }

    @Test
    public void getMedicalRecordByIdFail() {
        when(medicalRecordService.getMedicalRecordById(anyLong())).thenReturn(null);

        ResponseEntity<MedicalRecordDTO> response = medicalRecordController.getMedicalRecordById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(medicalRecordService, times(1)).getMedicalRecordById(1L);
    }

    @Test
    public void updateMedicalRecordSuccess() {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        MedicalRecordDTO updatedDTO = new MedicalRecordDTO();
        when(medicalRecordService.updateMedicalRecord(anyLong(), any(MedicalRecordDTO.class))).thenReturn(updatedDTO);

        ResponseEntity<MedicalRecordDTO> response = medicalRecordController.updateMedicalRecord(1L, medicalRecordDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDTO, response.getBody());
        verify(medicalRecordService, times(1)).updateMedicalRecord(1L, medicalRecordDTO);
    }

    @Test
    public void updateMedicalRecordFail() {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        when(medicalRecordService.updateMedicalRecord(anyLong(), any(MedicalRecordDTO.class))).thenReturn(null);

        ResponseEntity<MedicalRecordDTO> response = medicalRecordController.updateMedicalRecord(1L, medicalRecordDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(medicalRecordService, times(1)).updateMedicalRecord(1L, medicalRecordDTO);
    }

    @Test
    public void deleteMedicalRecordSuccess() {
        doNothing().when(medicalRecordService).deleteMedicalRecord(anyLong());

        ResponseEntity<Void> response = medicalRecordController.deleteMedicalRecord(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(medicalRecordService, times(1)).deleteMedicalRecord(1L);
    }

    @Test
    public void deleteMedicalRecordFail() {
        doThrow(new RuntimeException("Medical Record not found")).when(medicalRecordService).deleteMedicalRecord(anyLong());

        ResponseEntity<Void> response = medicalRecordController.deleteMedicalRecord(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(medicalRecordService, times(1)).deleteMedicalRecord(999L);
    }
}
