package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.PatientDTO;
import com.healthcaremanagement.HealthcareManagement.entity.PatientEntity;
import com.healthcaremanagement.HealthcareManagement.exception.PatientNotFoundException;
import com.healthcaremanagement.HealthcareManagement.exception.SaveErrorException;
import com.healthcaremanagement.HealthcareManagement.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    //convert entity to DTO
    public PatientDTO convertEntityToDTO(PatientEntity patientEntity){
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setFirstName(patientEntity.getFirstName());
        patientDTO.setLastName(patientEntity.getLastName());
        patientDTO.setDob(patientEntity.getDob());
        patientDTO.setEmail(patientEntity.getEmail());
        patientDTO.setPassword(patientEntity.getPassword());
        patientDTO.setAddress(patientEntity.getAddress());
        patientDTO.setGender(patientEntity.getGender());
        patientDTO.setPhone(patientEntity.getPhone());
        patientDTO.setAppointments(patientEntity.getAppointments());
        patientDTO.setBillings(patientEntity.getBillings());
        return patientDTO;
    }

    //convert DTO to entity
    public PatientEntity convertDTOToEntity(PatientDTO patientDTO){
        PatientEntity patientEntity = new PatientEntity();
        BeanUtils.copyProperties(patientDTO, patientEntity);
        return patientEntity;
    }

    //method to register user using UserDTO class
    public PatientDTO signUp(PatientDTO patientDTO) throws SaveErrorException {
        PatientEntity patientEntity = convertDTOToEntity(patientDTO);
        try {
            patientRepository.save(patientEntity);
            return convertEntityToDTO(patientEntity);
        } catch (Exception e) {
            throw new SaveErrorException("The Email and PhoneNumber already Exist!");
        }
    }


    public boolean login(PatientDTO patientDTO) {
        Optional<PatientEntity> patientEntityOptional = patientRepository.findByEmail(patientDTO.getEmail());
        if (patientEntityOptional.isPresent()) {
            PatientEntity userEntity1=patientEntityOptional.get();
            return userEntity1.getPassword().equals(patientDTO.getPassword());
        }
        return false;
    }

    public List<PatientDTO> getAllPatients() {
        List<PatientEntity> patientEntityList=patientRepository.findAll();
        List<PatientDTO> patientDTOList=new ArrayList<>();
        for(PatientEntity patientEntity:patientEntityList){
            PatientDTO patientDTO=convertEntityToDTO(patientEntity);
            patientDTOList.add(patientDTO);
        }
        return patientDTOList;
    }

    public PatientDTO getPatientById(Long patientId) throws PatientNotFoundException {
        Optional<PatientEntity> patientEntity = patientRepository.findById(patientId);
        if (patientEntity.isEmpty()) {
            throw new PatientNotFoundException("User not found with id " + patientId);
        } else {
            PatientEntity userEntity1 = patientEntity.get();
            return convertEntityToDTO(userEntity1);
        }
    }

    public PatientDTO updatePatient(Long patientId, PatientDTO patientDetails) {
        Optional<PatientEntity> patientEntity=patientRepository.findById(patientId);
        if(patientEntity.isPresent()) {
            PatientEntity patientEntity1 = patientEntity.get();
            patientEntity1.setFirstName(patientDetails.getFirstName());
            patientEntity1.setLastName(patientDetails.getLastName());
            patientEntity1.setDob(patientDetails.getDob());
            patientEntity1.setGender(patientDetails.getGender());
//            patientEntity1.setEmail(patientDetails.getEmail());
//            patientEntity1.setPassword(patientEntity1.getPassword());
            patientEntity1.setAddress(patientDetails.getAddress());
            patientEntity1.setPhone(patientDetails.getPhone());
            patientRepository.save(patientEntity1);
            return convertEntityToDTO(patientEntity1);
        }
        else {
            throw new RuntimeException("User not found with id " + patientId);
        }
    }

    public void deletePatient(Long patientId) {
        Optional<PatientEntity> patientEntity=patientRepository.findById(patientId);
        if(patientEntity.isPresent()){
            patientRepository.deleteById(patientId);
        }else{
            throw new RuntimeException("User not found with id " + patientId);
        }
    }
}
