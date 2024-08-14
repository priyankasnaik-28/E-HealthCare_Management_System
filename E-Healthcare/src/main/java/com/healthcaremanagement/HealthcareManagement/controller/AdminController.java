package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.AdminDTO;
import com.healthcaremanagement.HealthcareManagement.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<AdminDTO> createAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        AdminDTO createdAdmin = adminService.createAdmin(adminDTO);
        if (createdAdmin == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdAdmin);
    }


    @GetMapping("/getall")
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<AdminDTO> admins = adminService.getAllAdmins();
        if (admins == null || admins.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getAdminById(@PathVariable Long id) {
        AdminDTO adminDTO = adminService.getAdminById(id);
        if (adminDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adminDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDTO> updateAdmin(@Valid @PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        AdminDTO updatedAdmin = adminService.updateAdmin(id, adminDTO);
        if (updatedAdmin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAdmin);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        if (!adminService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        adminService.deleteAdmin(id);
        return ResponseEntity.ok().build();
    }

}

