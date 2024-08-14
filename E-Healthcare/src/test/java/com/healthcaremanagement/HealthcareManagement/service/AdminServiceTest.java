package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.AdminDTO;
import com.healthcaremanagement.HealthcareManagement.entity.AdminEntity;
import com.healthcaremanagement.HealthcareManagement.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    private AdminEntity adminEntity;
    private AdminDTO adminDTO;

    @BeforeEach
    void setUp() {
        adminEntity = new AdminEntity(1L, "John Doe", "john.doe@example.com", "password123");
        adminDTO = new AdminDTO(1L, "John Doe", "john.doe@example.com", "password123");
    }

    @Test
    void createAdmin_Success() {
        when(adminRepository.save(any(AdminEntity.class))).thenReturn(adminEntity);
        AdminDTO result = adminService.createAdmin(adminDTO);
        assertNotNull(result);
        assertEquals(adminDTO.getName(), result.getName());
        verify(adminRepository, times(1)).save(any(AdminEntity.class));
    }

    @Test
    void createAdmin_Failure() {
        when(adminRepository.save(any(AdminEntity.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.createAdmin(adminDTO);
        });

        assertEquals("Database error", exception.getMessage());
        verify(adminRepository, times(1)).save(any(AdminEntity.class));
    }

    @Test
    void getAllAdmins_Success() {
        when(adminRepository.findAll()).thenReturn(Arrays.asList(adminEntity));
        List<AdminDTO> admins = adminService.getAllAdmins();
        assertFalse(admins.isEmpty());
        assertEquals(1, admins.size());
        assertEquals(adminDTO.getName(), admins.get(0).getName());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void getAllAdmins_Failure() {
        when(adminRepository.findAll()).thenReturn(Collections.emptyList());

        List<AdminDTO> admins = adminService.getAllAdmins();

        assertTrue(admins.isEmpty());
        verify(adminRepository, times(1)).findAll();
    }


    @Test
    void deleteAdmin_Success() {
        doNothing().when(adminRepository).deleteById(adminEntity.getAdminId());
        adminService.deleteAdmin(adminEntity.getAdminId());
        verify(adminRepository, times(1)).deleteById(adminEntity.getAdminId());
    }

    @Test
    void deleteAdmin_Failure() {
        doThrow(new RuntimeException("Delete operation failed")).when(adminRepository).deleteById(adminEntity.getAdminId());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.deleteAdmin(adminEntity.getAdminId());
        });

        assertEquals("Delete operation failed", exception.getMessage());
        verify(adminRepository, times(1)).deleteById(adminEntity.getAdminId());
    }


    @Test
    void getAdminById_Success() {
        when(adminRepository.findById(adminEntity.getAdminId())).thenReturn(Optional.of(adminEntity));
        AdminDTO result = adminService.getAdminById(adminEntity.getAdminId());
        assertNotNull(result);
        assertEquals(adminDTO.getName(), result.getName());
        verify(adminRepository, times(1)).findById(adminEntity.getAdminId());
    }

    @Test
    void getAdminById_NotFound() {
        when(adminRepository.findById(anyLong())).thenReturn(Optional.empty());
        AdminDTO result = adminService.getAdminById(1L);
        assertNull(result);
        verify(adminRepository, times(1)).findById(1L);
    }

    @Test
    void updateAdmin_Success() {

        when(adminRepository.existsById(adminEntity.getAdminId())).thenReturn(true);

        // Simulate the entity after being updated in the repository
        AdminEntity updatedAdminEntity = new AdminEntity(adminEntity.getAdminId(), "Jane Doe", "jane.doe@example.com", "newpassword123");
        when(adminRepository.save(any(AdminEntity.class))).thenReturn(updatedAdminEntity);

        AdminDTO updatedDTO = new AdminDTO(1L, "Jane Doe", "jane.doe@example.com", "newpassword123");
        AdminDTO result = adminService.updateAdmin(adminEntity.getAdminId(), updatedDTO);

        assertNotNull(result);
        assertEquals(updatedDTO.getName(), result.getName());
        assertEquals(updatedDTO.getEmail(), result.getEmail());
        assertEquals(updatedDTO.getPassword(), result.getPassword());

        verify(adminRepository, times(1)).existsById(adminEntity.getAdminId());
        verify(adminRepository, times(1)).save(any(AdminEntity.class));
    }


    @Test
    void updateAdmin_NotFound() {
        when(adminRepository.existsById(anyLong())).thenReturn(false);

        AdminDTO updatedDTO = new AdminDTO(1L, "Jane Doe", "jane.doe@example.com", "newpassword123");
        AdminDTO result = adminService.updateAdmin(1L, updatedDTO);

        assertNull(result);
        verify(adminRepository, times(1)).existsById(1L);
        verify(adminRepository, never()).save(any(AdminEntity.class));
    }


}