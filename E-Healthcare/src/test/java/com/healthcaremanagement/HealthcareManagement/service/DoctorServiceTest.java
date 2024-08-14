package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.DoctorDTO;
import com.healthcaremanagement.HealthcareManagement.dto.DoctorDTO;
import com.healthcaremanagement.HealthcareManagement.entity.DoctorEntity;
import com.healthcaremanagement.HealthcareManagement.entity.DoctorEntity;
import com.healthcaremanagement.HealthcareManagement.repository.BillingRepository;
import com.healthcaremanagement.HealthcareManagement.repository.DoctorRepository;
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

public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createBilling_Success() throws ParseException {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1L);
        doctorDTO.setFirstName("firstname");
        doctorDTO.setLastName("lastname");
        doctorDTO.setSpecialization("dentist");
        doctorDTO.setSchedule("morning");

        DoctorEntity doctorEntity = new DoctorEntity();
        BeanUtils.copyProperties(doctorDTO, doctorEntity);

        when(doctorRepository.save(any(DoctorEntity.class))).thenReturn(doctorEntity);

        DoctorDTO result = doctorService.createDoctor(doctorDTO);

        assertEquals(doctorDTO.getFirstName(), result.getFirstName());
        assertEquals(doctorDTO.getSchedule(), result.getSchedule());
        assertEquals(doctorDTO.getSpecialization(), result.getSpecialization());
    }

    @Test
    public void createAppointment_Fail() {
        // This is a bit more complex as you need to simulate failure conditions
        // For instance, the repository might throw an exception if saving fails

        DoctorDTO doctorDTO = new DoctorDTO();
        DoctorEntity doctorEntity = new DoctorEntity();
        BeanUtils.copyProperties(doctorDTO, doctorEntity);

        when(doctorRepository.save(any(DoctorEntity.class))).thenThrow(new RuntimeException("Save failed"));

        // Expecting an exception during createAppointment
        assertThrows(RuntimeException.class, () -> doctorService.createDoctor(doctorDTO));
    }

    @Test
    public void getAllAppointments_Success() {

        List<DoctorEntity> medicalRecordEntities = Arrays.asList(
                new DoctorEntity(),
                new DoctorEntity());


        when(doctorRepository.findAll()).thenReturn(medicalRecordEntities);

        List<DoctorEntity> records = doctorRepository.findAll();
        assertEquals(2, records.size());

        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    public void getAllAppointments_Fail() {
        // Simulate a failure scenario
        when(doctorRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        // Expecting an exception during getAllAppointments
        assertThrows(RuntimeException.class, () -> doctorService.getAllDoctors());
    }

    @Test
    public void getAppointmentById_Success() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1L);

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setDoctorId(1L);

        when(doctorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(doctorEntity));

        DoctorDTO result = doctorService.getDoctorById(1L);

        assertEquals(doctorDTO.getDoctorId(), result.getDoctorId());
    }

    @Test
    public void getAppointmentById_Fail() {
        // Simulate appointment not found
        when(doctorRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        DoctorDTO result = doctorService.getDoctorById(1L);

        assertEquals(null, result);
    }

    @Test
    public void updateAppointment_Success() {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setDoctorId(1L);

        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setDoctorId(1L);

        when(doctorRepository.existsById(anyLong())).thenReturn(true);
        when(doctorRepository.save(any(DoctorEntity.class))).thenReturn(doctorEntity);

        DoctorDTO result = doctorService.updateDoctor(1L, doctorDTO);

        assertEquals(doctorDTO.getDoctorId(), result.getDoctorId());
    }

    @Test
    public void updateAppointment_Fail() {
        // Simulate the case where the appointment does not exist
        when(doctorRepository.existsById(anyLong())).thenReturn(false);

        DoctorDTO doctorDTO = new DoctorDTO();
        DoctorDTO result = doctorService.updateDoctor(1L, doctorDTO);

        assertEquals(null, result);
    }


    @Test
    public void deleteBilling_Success() {
        doNothing().when(doctorRepository).deleteById(anyLong());

        doctorService.deleteDoctor(1L);

        verify(doctorRepository).deleteById(1L);
    }

    @Test
    public void deleteBilling_Fail() {
        doThrow(new RuntimeException("Delete failed")).when(doctorRepository).deleteById(anyLong());

        assertThrows(RuntimeException.class, () -> doctorService.deleteDoctor(1L));
    }

}
