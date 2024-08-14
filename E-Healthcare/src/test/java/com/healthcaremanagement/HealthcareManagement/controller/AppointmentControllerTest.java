package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.AppointmentDTO;
import com.healthcaremanagement.HealthcareManagement.service.AppointmentService;
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

public class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAppointmentSuccess() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        when(appointmentService.createAppointment(any(AppointmentDTO.class))).thenReturn(appointmentDTO);

        ResponseEntity<AppointmentDTO> response = appointmentController.createAppointment(appointmentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointmentDTO, response.getBody());
    }

    @Test
    public void getAllAppointmentsSuccess() {
        AppointmentDTO appointmentDTO1 = new AppointmentDTO();
        AppointmentDTO appointmentDTO2 = new AppointmentDTO();
        List<AppointmentDTO> list = Arrays.asList(appointmentDTO1, appointmentDTO2);

        when(appointmentService.getAllAppointments()).thenReturn(list);

        ResponseEntity<List<AppointmentDTO>> response = appointmentController.getAllAppointments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());

        verify(appointmentService, times(1)).getAllAppointments();
    }

    @Test
    public void getAppointmentByIdSuccess() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        when(appointmentService.getAppointmentById(anyLong())).thenReturn(appointmentDTO);

        ResponseEntity<AppointmentDTO> response = appointmentController.getAppointmentById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointmentDTO, response.getBody());

        verify(appointmentService, times(1)).getAppointmentById(1L);
    }

    @Test
    public void getAppointmentByIdFail() {
        when(appointmentService.getAppointmentById(anyLong())).thenReturn(null);

        ResponseEntity<AppointmentDTO> response = appointmentController.getAppointmentById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(appointmentService, times(1)).getAppointmentById(1L);
    }

    @Test
    public void updateAppointmentSuccess() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        AppointmentDTO updatedAppointmentDTO = new AppointmentDTO();

        when(appointmentService.updateAppointment(anyLong(), any(AppointmentDTO.class))).thenReturn(updatedAppointmentDTO);

        ResponseEntity<AppointmentDTO> response = appointmentController.updateAppointment(1L, appointmentDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAppointmentDTO, response.getBody());

        verify(appointmentService, times(1)).updateAppointment(1L, appointmentDTO);
    }

    @Test
    public void updateAppointmentFail() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        when(appointmentService.updateAppointment(anyLong(), any(AppointmentDTO.class))).thenReturn(null);

        ResponseEntity<AppointmentDTO> response = appointmentController.updateAppointment(1L, appointmentDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(appointmentService, times(1)).updateAppointment(1L, appointmentDTO);
    }

    @Test
    public void deleteAppointmentSuccess() {
        doNothing().when(appointmentService).deleteAppointment(anyLong());

        ResponseEntity<Void> response = appointmentController.deleteAppointment(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(appointmentService, times(1)).deleteAppointment(1L);
    }

    @Test
    public void deleteAppointmentFail() {
        doThrow(new RuntimeException("Appointment not found")).when(appointmentService).deleteAppointment(anyLong());

        ResponseEntity<Void> response = appointmentController.deleteAppointment(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(appointmentService, times(1)).deleteAppointment(999L);
    }
}
