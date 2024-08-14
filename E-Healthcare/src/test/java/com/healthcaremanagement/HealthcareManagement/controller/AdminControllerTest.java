package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.AdminDTO;
import com.healthcaremanagement.HealthcareManagement.entity.AdminEntity;
import com.healthcaremanagement.HealthcareManagement.service.AdminService;
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
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void savePatientSuccess() {
        AdminEntity adminEntity = new AdminEntity();
        AdminDTO adminDTO = new AdminDTO();
        when(adminService.createAdmin(any(AdminDTO.class))).thenReturn(adminDTO);

        ResponseEntity<AdminDTO> response = adminController.createAdmin(adminDTO);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), adminDTO);
    }

    @Test
    public void savePatientFail() {
        AdminDTO invalidAdminDTO = new AdminDTO(); // Assume this DTO is invalid or incomplete

        // Assuming service layer returns null or an exception is thrown
        when(adminService.createAdmin(any(AdminDTO.class))).thenReturn(null);

        ResponseEntity<AdminDTO> response = adminController.createAdmin(invalidAdminDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getAllTest() {
        AdminDTO adminDTO = new AdminDTO();
        AdminDTO adminDTO1 = new AdminDTO();
        List<AdminDTO> list = Arrays.asList(adminDTO, adminDTO1);

        when(adminService.getAllAdmins()).thenReturn(list);

        ResponseEntity<List<AdminDTO>> response = adminController.getAllAdmins();

        assertEquals(response.getStatusCode(),HttpStatus.OK);
        assertEquals(response.getBody(),list);

        verify(adminService, times(1)).getAllAdmins();
    }

    @Test
    public void getById_Fail() {
        // Assuming service layer returns null indicating admin not found
        when(adminService.getAdminById(anyLong())).thenReturn(null);

        ResponseEntity<AdminDTO> response = adminController.getAdminById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(adminService, times(1)).getAdminById(1L);
    }


    @Test
    public void getByIdSuccess() {

        AdminDTO adminDTO = new AdminDTO();

        when(adminService.getAdminById(anyLong())).thenReturn(adminDTO);

        ResponseEntity<AdminDTO> response = adminController.getAdminById(1L);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        verify(adminService, times(1)).getAdminById(1L);
    }

    @Test
    public void deleteById() {
        doNothing().when(adminService).deleteAdmin(anyLong());
        ResponseEntity<?> response = adminController.deleteAdmin(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void deleteById_Fail() {
        // Assuming service layer throws an exception or returns void for a non-existent admin
        doNothing().when(adminService).deleteAdmin(anyLong());

        ResponseEntity<?> response = adminController.deleteAdmin(999L); // Assuming ID 999 does not exist
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateSuccess() {
        AdminDTO adminDTO = new AdminDTO();
        AdminDTO updatedDto = new AdminDTO();

        // Mock the updateAdmin method to return the updatedDto
        when(adminService.updateAdmin(anyLong(), any(AdminDTO.class))).thenReturn(updatedDto);

        // Perform the update operation
        ResponseEntity<AdminDTO> response = adminController.updateAdmin(1L, adminDTO);

        // Assert that the response status is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedDto, response.getBody());

        // Verify that updateAdmin was called once with the provided ID and DTO
        verify(adminService, times(1)).updateAdmin(1L, adminDTO);
    }


    @Test
    public void updateAdmin_Fail() {
        AdminDTO adminDTO = new AdminDTO();
        AdminDTO updatedDto = null; // Service returns null to indicate failure

        when(adminService.updateAdmin(anyLong(), any(AdminDTO.class))).thenReturn(updatedDto);
        ResponseEntity<AdminDTO> response = adminController.updateAdmin(1L, adminDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(adminService, times(1)).updateAdmin(1L, adminDTO);
    }

}
