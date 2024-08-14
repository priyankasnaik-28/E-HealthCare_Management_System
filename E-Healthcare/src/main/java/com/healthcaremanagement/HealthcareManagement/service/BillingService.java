package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.AppointmentDTO;
import com.healthcaremanagement.HealthcareManagement.dto.BillingDTO;
import com.healthcaremanagement.HealthcareManagement.entity.AppointmentEntity;
import com.healthcaremanagement.HealthcareManagement.entity.BillingEntity;
import com.healthcaremanagement.HealthcareManagement.repository.BillingRepository;
import com.healthcaremanagement.HealthcareManagement.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;



    @Autowired
    private PatientRepository patientRepository;

    //convert entity to DTO
    public BillingDTO convertEntityToDTO(BillingEntity billingEntity){
        BillingDTO billingDTO = new BillingDTO();
        BeanUtils.copyProperties(billingEntity, billingDTO);
        return billingDTO;
    }

    //convert DTO to entity
    public BillingEntity convertDTOToEntity(BillingDTO billingDTO){
        BillingEntity billingEntity = new BillingEntity();
        BeanUtils.copyProperties(billingDTO, billingEntity);
        return billingEntity;
    }

    public BillingDTO createBilling(BillingDTO billingDTO) {
        BillingEntity billingEntity = convertDTOToEntity(billingDTO);
        BillingEntity savedBilling = billingRepository.save(billingEntity);
        return convertEntityToDTO(savedBilling);
    }

    public List<BillingDTO> getAllBillings() {
        return billingRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public BillingDTO getBillingById(Long billingId) {
        return billingRepository.findById(billingId).map(this::convertEntityToDTO).orElse(null);
    }

    public BillingDTO updateBilling(Long billingId, BillingDTO billingDTO) {
        if (billingRepository.existsById(billingId)) {
            BillingEntity billingEntity = convertDTOToEntity(billingDTO);
            billingEntity.setBillingId(billingId);
            BillingEntity updatedBilling = billingRepository.save(billingEntity);
            return convertEntityToDTO(updatedBilling);
        }
        return null;
    }

    public void deleteBilling(Long billingId) {
        billingRepository.deleteById(billingId);
    }

//    private BillingDTO entityToDto(BillingEntity billingEntity) {
//        BillingDTO billingDTO = new BillingDTO();
//        billingDTO.setBillingId(billingEntity.getBillingId());
//        billingDTO.setPatientId(billingEntity.getPatient().getPatientId());
//        billingDTO.setAmount(billingEntity.getAmount());
//        billingDTO.setBillingDate(billingEntity.getBillingDate());
//        billingDTO.setPaymentStatus(billingEntity.getPaymentStatus());
//        return billingDTO;
//    }
//
//    private BillingEntity dtoToEntity(BillingDTO billingDTO) {
//        BillingEntity billingEntity = new BillingEntity();
//        billingEntity.setAmount(billingDTO.getAmount());
//        billingEntity.setBillingDate(billingDTO.getBillingDate());
//        billingEntity.setPaymentStatus(billingDTO.getPaymentStatus());
//
//        PatientEntity patientEntity = patientRepository.findById(billingDTO.getPatientId()).orElse(null);
//        billingEntity.setPatient(patientEntity);
//
//        return billingEntity;
//    }
}
