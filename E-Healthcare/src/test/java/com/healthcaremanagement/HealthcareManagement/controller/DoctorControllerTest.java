package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.DoctorDTO;
import com.healthcaremanagement.HealthcareManagement.service.DoctorService;
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

public class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createDoctorSuccess() {
        DoctorDTO doctorDTO = new DoctorDTO();
        when(doctorService.createDoctor(any(DoctorDTO.class))).thenReturn(doctorDTO);

        ResponseEntity<DoctorDTO> response = doctorController.createDoctor(doctorDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(doctorDTO, response.getBody());
        verify(doctorService, times(1)).createDoctor(doctorDTO);
    }

    @Test
    public void getAllDoctorsSuccess() {
        DoctorDTO doctorDTO1 = new DoctorDTO();
        DoctorDTO doctorDTO2 = new DoctorDTO();
        List<DoctorDTO> doctorList = Arrays.asList(doctorDTO1, doctorDTO2);

        when(doctorService.getAllDoctors()).thenReturn(doctorList);

        ResponseEntity<List<DoctorDTO>> response = doctorController.getAllDoctors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(doctorList, response.getBody());
        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    public void getDoctorByIdSuccess() {
        DoctorDTO doctorDTO = new DoctorDTO();

        when(doctorService.getDoctorById(anyLong())).thenReturn(doctorDTO);

        ResponseEntity<DoctorDTO> response = doctorController.getDoctorById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(doctorDTO, response.getBody());
        verify(doctorService, times(1)).getDoctorById(1L);
    }

    @Test
    public void getDoctorByIdFail() {
        when(doctorService.getDoctorById(anyLong())).thenReturn(null);

        ResponseEntity<DoctorDTO> response = doctorController.getDoctorById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(doctorService, times(1)).getDoctorById(1L);
    }

    @Test
    public void updateDoctorSuccess() {
        DoctorDTO doctorDTO = new DoctorDTO();
        DoctorDTO updatedDoctorDTO = new DoctorDTO();

        when(doctorService.updateDoctor(anyLong(), any(DoctorDTO.class))).thenReturn(updatedDoctorDTO);

        ResponseEntity<DoctorDTO> response = doctorController.updateDoctor(1L, doctorDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDoctorDTO, response.getBody());
        verify(doctorService, times(1)).updateDoctor(1L, doctorDTO);
    }

    @Test
    public void updateDoctorFail() {
        DoctorDTO doctorDTO = new DoctorDTO();

        when(doctorService.updateDoctor(anyLong(), any(DoctorDTO.class))).thenReturn(null);

        ResponseEntity<DoctorDTO> response = doctorController.updateDoctor(1L, doctorDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(doctorService, times(1)).updateDoctor(1L, doctorDTO);
    }

    @Test
    public void deleteDoctorSuccess() {
        doNothing().when(doctorService).deleteDoctor(anyLong());

        ResponseEntity<Void> response = doctorController.deleteDoctor(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(doctorService, times(1)).deleteDoctor(1L);
    }

    @Test
    public void deleteDoctorFail() {
        doThrow(new RuntimeException("Doctor not found")).when(doctorService).deleteDoctor(anyLong());

        ResponseEntity<Void> response = doctorController.deleteDoctor(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(doctorService, times(1)).deleteDoctor(999L);
    }
}
