package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.AppointmentDTO;
import com.healthcaremanagement.HealthcareManagement.entity.AppointmentEntity;
import com.healthcaremanagement.HealthcareManagement.repository.AppointmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAppointment_Success() throws ParseException {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
       appointmentDTO.setAppointmentId(1L);
        appointmentDTO.setAppointmentDate(parseDate("2024-08-01"));
        appointmentDTO.setAppointmentTime(parseTime("10:00"));
        appointmentDTO.setStatus("Scheduled");

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        BeanUtils.copyProperties(appointmentDTO, appointmentEntity);

        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointmentEntity);

        AppointmentDTO result = appointmentService.createAppointment(appointmentDTO);

        assertEquals(appointmentDTO.getAppointmentDate(), result.getAppointmentDate());
        assertEquals(appointmentDTO.getAppointmentTime(), result.getAppointmentTime());
        assertEquals(appointmentDTO.getStatus(), result.getStatus());
    }

    @Test
    public void createAppointment_Fail() {
        // This is a bit more complex as you need to simulate failure conditions
        // For instance, the repository might throw an exception if saving fails

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        BeanUtils.copyProperties(appointmentDTO, appointmentEntity);

        when(appointmentRepository.save(any(AppointmentEntity.class))).thenThrow(new RuntimeException("Save failed"));

        // Expecting an exception during createAppointment
        assertThrows(RuntimeException.class, () -> appointmentService.createAppointment(appointmentDTO));
    }

    @Test
    public void getAllAppointments_Success() {

        List<AppointmentEntity> medicalRecordEntities = Arrays.asList(
                new AppointmentEntity(),
                new AppointmentEntity());


        when(appointmentRepository.findAll()).thenReturn(medicalRecordEntities);

        List<AppointmentEntity> records = appointmentRepository.findAll();
        assertEquals(2, records.size());

        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    public void getAllAppointments_Fail() {
        // Simulate a failure scenario
        when(appointmentRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        // Expecting an exception during getAllAppointments
        assertThrows(RuntimeException.class, () -> appointmentService.getAllAppointments());
    }

    @Test
    public void getAppointmentById_Success() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1L);

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentId(1L);

        when(appointmentRepository.findById(anyLong())).thenReturn(java.util.Optional.of(appointmentEntity));

        AppointmentDTO result = appointmentService.getAppointmentById(1L);

        assertEquals(appointmentDTO.getAppointmentId(), result.getAppointmentId());
    }

    @Test
    public void getAppointmentById_Fail() {
        // Simulate appointment not found
        when(appointmentRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        AppointmentDTO result = appointmentService.getAppointmentById(1L);

        assertEquals(null, result);
    }

    @Test
    public void updateAppointment_Success() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentId(1L);

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentId(1L);

        when(appointmentRepository.existsById(anyLong())).thenReturn(true);
        when(appointmentRepository.save(any(AppointmentEntity.class))).thenReturn(appointmentEntity);

        AppointmentDTO result = appointmentService.updateAppointment(1L, appointmentDTO);

        assertEquals(appointmentDTO.getAppointmentId(), result.getAppointmentId());
    }

    @Test
    public void updateAppointment_Fail() {
        // Simulate the case where the appointment does not exist
        when(appointmentRepository.existsById(anyLong())).thenReturn(false);

        AppointmentDTO appointmentDTO = new AppointmentDTO();
        AppointmentDTO result = appointmentService.updateAppointment(1L, appointmentDTO);

        assertEquals(null, result);
    }


    @Test
    public void deleteAppointment_Success() {
        doNothing().when(appointmentRepository).deleteById(anyLong());

        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository).deleteById(1L);
    }

    @Test
    public void deleteAppointment_Fail() {
        doThrow(new RuntimeException("Delete failed")).when(appointmentRepository).deleteById(anyLong());

        assertThrows(RuntimeException.class, () -> appointmentService.deleteAppointment(1L));
    }

    public static Date parseDate(String dateString) throws ParseException {
        // Define the expected date format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Parse and return the Date
        return formatter.parse(dateString);
    }

    public static Time parseTime(String timeString) throws DateTimeParseException {
        // Parse the String to LocalTime first
        LocalTime localTime = LocalTime.parse(timeString);
        // Convert LocalTime to java.sql.Time
        return Time.valueOf(localTime);
    }
}
