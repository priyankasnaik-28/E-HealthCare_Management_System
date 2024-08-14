package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.AdminDTO;
import com.healthcaremanagement.HealthcareManagement.entity.AdminEntity;
import com.healthcaremanagement.HealthcareManagement.repository.AdminRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Convert entity to DTO
    public AdminDTO convertEntityToDTO(AdminEntity adminEntity) {
        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(adminEntity, adminDTO);
        return adminDTO;
    }

    // Convert DTO to entity
    public AdminEntity convertDTOToEntity(AdminDTO adminDTO) {
        AdminEntity adminEntity = new AdminEntity();
        BeanUtils.copyProperties(adminDTO, adminEntity);
        return adminEntity;
    }

    public AdminDTO createAdmin(AdminDTO adminDTO) {
        AdminEntity adminEntity = convertDTOToEntity(adminDTO);
        AdminEntity savedAdmin = adminRepository.save(adminEntity);
        return convertEntityToDTO(savedAdmin);
    }

    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public AdminDTO getAdminById(Long adminId) {
        return adminRepository.findById(adminId).map(this::convertEntityToDTO).orElse(null);
    }

    public AdminDTO updateAdmin(Long adminId, AdminDTO adminDTO) {
        if (adminRepository.existsById(adminId)) {
            AdminEntity adminEntity = convertDTOToEntity(adminDTO);
            adminEntity.setAdminId(adminId);
            AdminEntity updatedAdmin = adminRepository.save(adminEntity);
            return convertEntityToDTO(updatedAdmin);
        }
        return null;
    }

    public boolean existsById(Long id) {
        return adminRepository.existsById(id);
    }
    public void deleteAdmin(Long adminId) {
        adminRepository.deleteById(adminId);
    }
}
