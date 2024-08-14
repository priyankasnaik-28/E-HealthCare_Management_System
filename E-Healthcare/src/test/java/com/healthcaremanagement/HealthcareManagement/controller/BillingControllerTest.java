package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.BillingDTO;
import com.healthcaremanagement.HealthcareManagement.service.BillingService;
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

public class BillingControllerTest {

    @Mock
    private BillingService billingService;

    @InjectMocks
    private BillingController billingController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createBillingSuccess() {
        BillingDTO billingDTO = new BillingDTO();
        when(billingService.createBilling(any(BillingDTO.class))).thenReturn(billingDTO);

        ResponseEntity<BillingDTO> response = billingController.createBilling(billingDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(billingDTO, response.getBody());
        verify(billingService, times(1)).createBilling(billingDTO);
    }

    @Test
    public void getAllBillingsSuccess() {
        BillingDTO billingDTO1 = new BillingDTO();
        BillingDTO billingDTO2 = new BillingDTO();
        List<BillingDTO> billingList = Arrays.asList(billingDTO1, billingDTO2);

        when(billingService.getAllBillings()).thenReturn(billingList);

        ResponseEntity<List<BillingDTO>> response = billingController.getAllBillings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(billingList, response.getBody());
        verify(billingService, times(1)).getAllBillings();
    }

    @Test
    public void getBillingByIdSuccess() {
        BillingDTO billingDTO = new BillingDTO();

        when(billingService.getBillingById(anyLong())).thenReturn(billingDTO);

        ResponseEntity<BillingDTO> response = billingController.getBillingById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(billingDTO, response.getBody());
        verify(billingService, times(1)).getBillingById(1L);
    }

    @Test
    public void getBillingByIdFail() {
        when(billingService.getBillingById(anyLong())).thenReturn(null);

        ResponseEntity<BillingDTO> response = billingController.getBillingById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(billingService, times(1)).getBillingById(1L);
    }

    @Test
    public void updateBillingSuccess() {
        BillingDTO billingDTO = new BillingDTO();
        BillingDTO updatedBillingDTO = new BillingDTO();

        when(billingService.updateBilling(anyLong(), any(BillingDTO.class))).thenReturn(updatedBillingDTO);

        ResponseEntity<BillingDTO> response = billingController.updateBilling(1L, billingDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBillingDTO, response.getBody());
        verify(billingService, times(1)).updateBilling(1L, billingDTO);
    }

    @Test
    public void updateBillingFail() {
        BillingDTO billingDTO = new BillingDTO();

        when(billingService.updateBilling(anyLong(), any(BillingDTO.class))).thenReturn(null);

        ResponseEntity<BillingDTO> response = billingController.updateBilling(1L, billingDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(billingService, times(1)).updateBilling(1L, billingDTO);
    }

    @Test
    public void deleteBillingSuccess() {
        doNothing().when(billingService).deleteBilling(anyLong());

        ResponseEntity<Void> response = billingController.deleteBilling(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(billingService, times(1)).deleteBilling(1L);
    }

    @Test
    public void deleteBillingFail() {
        doThrow(new RuntimeException("Billing not found")).when(billingService).deleteBilling(anyLong());

        ResponseEntity<Void> response = billingController.deleteBilling(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(billingService, times(1)).deleteBilling(999L);
    }
}
