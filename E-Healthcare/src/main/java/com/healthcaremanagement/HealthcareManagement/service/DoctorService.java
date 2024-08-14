package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.AppointmentDTO;
import com.healthcaremanagement.HealthcareManagement.dto.DoctorDTO;
import com.healthcaremanagement.HealthcareManagement.entity.AppointmentEntity;
import com.healthcaremanagement.HealthcareManagement.entity.DoctorEntity;
import com.healthcaremanagement.HealthcareManagement.repository.DoctorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;


    //convert entity to DTO
    private DoctorDTO convertEntityToDTO(DoctorEntity doctorEntity){
        DoctorDTO doctorDTO = new DoctorDTO();
        BeanUtils.copyProperties(doctorEntity, doctorDTO);
        return doctorDTO;
    }

    //convert DTO to entity
    private DoctorEntity convertDTOToEntity(DoctorDTO doctorDTO){
        DoctorEntity doctorEntity = new DoctorEntity();
        BeanUtils.copyProperties(doctorDTO, doctorEntity);
        return doctorEntity;
    }

    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        DoctorEntity doctorEntity = convertDTOToEntity(doctorDTO);
        DoctorEntity savedDoctor = doctorRepository.save(doctorEntity);
        return convertEntityToDTO(savedDoctor);
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public DoctorDTO getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId).map(this::convertEntityToDTO).orElse(null);
    }

    public DoctorDTO updateDoctor(Long doctorId, DoctorDTO doctorDTO) {
        if (doctorRepository.existsById(doctorId)) {
            DoctorEntity doctorEntity = convertDTOToEntity(doctorDTO);
            doctorEntity.setDoctorId(doctorId);
            DoctorEntity updatedDoctor = doctorRepository.save(doctorEntity);
            return convertEntityToDTO(updatedDoctor);
        }
        return null;
    }

    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

//    private DoctorDTO entityToDto(DoctorEntity doctorEntity) {
//        DoctorDTO doctorDTO = new DoctorDTO();
//        doctorDTO.setDoctorId(doctorEntity.getDoctorId());
//        doctorDTO.setFirstName(doctorEntity.getFirstName());
//        doctorDTO.setLastName(doctorEntity.getLastName());
//        doctorDTO.setSpecialization(doctorEntity.getSpecialization());
//        doctorDTO.setSchedule(doctorEntity.getSchedule());
//        return doctorDTO;
//    }
//
//    private DoctorEntity dtoToEntity(DoctorDTO doctorDTO) {
//        DoctorEntity doctorEntity = new DoctorEntity();
//        doctorEntity.setFirstName(doctorDTO.getFirstName());
//        doctorEntity.setLastName(doctorDTO.getLastName());
//        doctorEntity.setSpecialization(doctorDTO.getSpecialization());
//        doctorEntity.setSchedule(doctorDTO.getSchedule());
//        return doctorEntity;
//    }
}
