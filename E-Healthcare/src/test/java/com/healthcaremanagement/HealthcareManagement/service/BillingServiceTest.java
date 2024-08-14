package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.BillingDTO;
import com.healthcaremanagement.HealthcareManagement.entity.BillingEntity;
import com.healthcaremanagement.HealthcareManagement.repository.BillingRepository;
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

public class BillingServiceTest {

    @Mock
    private BillingRepository billingRepository;

    @InjectMocks
    private BillingService billingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createBilling_Success() throws ParseException {
        BillingDTO billingDTO = new BillingDTO();
        billingDTO.setBillingId(1L);
        billingDTO.setBillingDate(parseDate("2024-08-01"));
        billingDTO.setAmount(10000.00);
        billingDTO.setPaymentStatus("success");

        BillingEntity billingEntity = new BillingEntity();
        BeanUtils.copyProperties(billingDTO, billingEntity);

        when(billingRepository.save(any(BillingEntity.class))).thenReturn(billingEntity);

        BillingDTO result = billingService.createBilling(billingDTO);

        assertEquals(billingDTO.getBillingDate(), result.getBillingDate());
        assertEquals(billingDTO.getAmount(), result.getAmount());
        assertEquals(billingDTO.getPaymentStatus(), result.getPaymentStatus());
    }

    @Test
    public void createAppointment_Fail() {
        // This is a bit more complex as you need to simulate failure conditions
        // For instance, the repository might throw an exception if saving fails

        BillingDTO billingDTO = new BillingDTO();
        BillingEntity billingEntity = new BillingEntity();
        BeanUtils.copyProperties(billingDTO, billingEntity);

        when(billingRepository.save(any(BillingEntity.class))).thenThrow(new RuntimeException("Save failed"));

        // Expecting an exception during createAppointment
        assertThrows(RuntimeException.class, () -> billingService.createBilling(billingDTO));
    }

    @Test
    public void getAllAppointments_Success() {

        List<BillingEntity> medicalRecordEntities = Arrays.asList(
                new BillingEntity(),
                new BillingEntity());


        when(billingRepository.findAll()).thenReturn(medicalRecordEntities);

        List<BillingEntity> records = billingRepository.findAll();
        assertEquals(2, records.size());

        verify(billingRepository, times(1)).findAll();
    }

    @Test
    public void getAllAppointments_Fail() {
        // Simulate a failure scenario
        when(billingRepository.findAll()).thenThrow(new RuntimeException("Fetch failed"));

        // Expecting an exception during getAllAppointments
        assertThrows(RuntimeException.class, () -> billingService.getAllBillings());
    }

    @Test
    public void getAppointmentById_Success() {
        BillingDTO billingDTO = new BillingDTO();
        billingDTO.setBillingId(1L);

        BillingEntity billingEntity = new BillingEntity();
        billingEntity.setBillingId(1L);

        when(billingRepository.findById(anyLong())).thenReturn(java.util.Optional.of(billingEntity));

        BillingDTO result = billingService.getBillingById(1L);

        assertEquals(billingDTO.getBillingId(), result.getBillingId());
    }

    @Test
    public void getAppointmentById_Fail() {
        // Simulate appointment not found
        when(billingRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        BillingDTO result = billingService.getBillingById(1L);

        assertEquals(null, result);
    }

    @Test
    public void updateAppointment_Success() {
        BillingDTO billingDTO = new BillingDTO();
        billingDTO.setBillingId(1L);

        BillingEntity billingEntity = new BillingEntity();
        billingEntity.setBillingId(1L);

        when(billingRepository.existsById(anyLong())).thenReturn(true);
        when(billingRepository.save(any(BillingEntity.class))).thenReturn(billingEntity);

        BillingDTO result = billingService.updateBilling(1L, billingDTO);

        assertEquals(billingDTO.getBillingId(), result.getBillingId());
    }

    @Test
    public void updateAppointment_Fail() {
        // Simulate the case where the appointment does not exist
        when(billingRepository.existsById(anyLong())).thenReturn(false);

        BillingDTO billingDTO = new BillingDTO();
        BillingDTO result = billingService.updateBilling(1L, billingDTO);

        assertEquals(null, result);
    }


    @Test
    public void deleteBilling_Success() {
        doNothing().when(billingRepository).deleteById(anyLong());

        billingService.deleteBilling(1L);

        verify(billingRepository).deleteById(1L);
    }

    @Test
    public void deleteBilling_Fail() {
        doThrow(new RuntimeException("Delete failed")).when(billingRepository).deleteById(anyLong());

        assertThrows(RuntimeException.class, () -> billingService.deleteBilling(1L));
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
