package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.MedicalRecordDTO;
import com.healthcaremanagement.HealthcareManagement.dto.MedicalRecordDTO;
import com.healthcaremanagement.HealthcareManagement.entity.MedicalRecordEntity;
import com.healthcaremanagement.HealthcareManagement.entity.MedicalRecordEntity;
import com.healthcaremanagement.HealthcareManagement.repository.MedicalRecordRepository;
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

public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAppointment_Success() throws ParseException {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
       medicalRecordDTO.setMedicalRecordId(1L);
        medicalRecordDTO.setRecord("heavy fever");


        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        BeanUtils.copyProperties(medicalRecordDTO, medicalRecordEntity);

        when(medicalRecordRepository.save(any(MedicalRecordEntity.class))).thenReturn(medicalRecordEntity);

        MedicalRecordDTO result = medicalRecordService.createMedicalRecord(medicalRecordDTO);

        assertEquals(medicalRecordDTO.getMedicalRecordId(), result.getMedicalRecordId());
        assertEquals(medicalRecordDTO.getRecord(), result.getRecord());
    }

    @Test
    public void createAppointment_Fail() {
        // This is a bit more complex as you need to simulate failure conditions
        // For instance, the repository might throw an exception if saving fails

        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        BeanUtils.copyProperties(medicalRecordDTO, medicalRecordEntity);

        when(medicalRecordRepository.save(any(MedicalRecordEntity.class))).thenThrow(new RuntimeException("Save failed"));

        // Expecting an exception during createAppointment
        assertThrows(RuntimeException.class, () -> medicalRecordService.createMedicalRecord(medicalRecordDTO));
    }

    @Test
    public void getAllAppointments_Success() {

        List<MedicalRecordEntity> medicalRecordEntities = Arrays.asList(
                new MedicalRecordEntity(),
                new MedicalRecordEntity());


        when(medicalRecordRepository.findAll()).thenReturn(medicalRecordEntities);

        List<MedicalRecordEntity> records = medicalRecordRepository.findAll();
        assertEquals(2, records.size());

        verify(medicalRecordRepository, times(1)).findAll();
    }

    @Test
    public void getAllAppointments_Fail() {
        // Simulate a failure scenario
        when(medicalRecordRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        // Expecting an exception during getAllAppointments
        assertThrows(RuntimeException.class, () -> medicalRecordService.getAllMedicalRecords());
    }

    @Test
    public void getAppointmentById_Success() {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setMedicalRecordId(1L);

        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        medicalRecordEntity.setMedicalRecordId(1L);

        when(medicalRecordRepository.findById(anyLong())).thenReturn(java.util.Optional.of(medicalRecordEntity));

        MedicalRecordDTO result = medicalRecordService.getMedicalRecordById(1L);

        assertEquals(medicalRecordDTO.getMedicalRecordId(), result.getMedicalRecordId());
    }

    @Test
    public void getAppointmentById_Fail() {
        // Simulate appointment not found
        when(medicalRecordRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        MedicalRecordDTO result = medicalRecordService.getMedicalRecordById(1L);

        assertEquals(null, result);
    }

    @Test
    public void updateAppointment_Success() {
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        medicalRecordDTO.setMedicalRecordId(1L);

        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        medicalRecordEntity.setMedicalRecordId(1L);

        when(medicalRecordRepository.existsById(anyLong())).thenReturn(true);
        when(medicalRecordRepository.save(any(MedicalRecordEntity.class))).thenReturn(medicalRecordEntity);

        MedicalRecordDTO result = medicalRecordService.updateMedicalRecord(1L, medicalRecordDTO);

        assertEquals(medicalRecordDTO.getMedicalRecordId(), result.getMedicalRecordId());
    }

    @Test
    public void updateAppointment_Fail() {
        // Simulate the case where the appointment does not exist
        when(medicalRecordRepository.existsById(anyLong())).thenReturn(false);

        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
        MedicalRecordDTO result = medicalRecordService.updateMedicalRecord(1L, medicalRecordDTO);

        assertEquals(null, result);
    }


    @Test
    public void deleteAppointment_Success() {
        doNothing().when(medicalRecordRepository).deleteById(anyLong());

        medicalRecordService.deleteMedicalRecord(1L);

        verify(medicalRecordRepository).deleteById(1L);
    }

    @Test
    public void deleteAppointment_Fail() {
        doThrow(new RuntimeException("Delete failed")).when(medicalRecordRepository).deleteById(anyLong());

        assertThrows(RuntimeException.class, () -> medicalRecordService.deleteMedicalRecord(1L));
    }

}
