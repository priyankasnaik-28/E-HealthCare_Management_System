package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.dto.AppointmentDTO;
import com.healthcaremanagement.HealthcareManagement.dto.PatientDTO;
import com.healthcaremanagement.HealthcareManagement.entity.AppointmentEntity;
import com.healthcaremanagement.HealthcareManagement.entity.DoctorEntity;
import com.healthcaremanagement.HealthcareManagement.entity.PatientEntity;
import com.healthcaremanagement.HealthcareManagement.repository.AppointmentRepository;
import com.healthcaremanagement.HealthcareManagement.repository.DoctorRepository;
import com.healthcaremanagement.HealthcareManagement.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;


    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    //convert entity to DTO
    private AppointmentDTO convertEntityToDTO(AppointmentEntity appointmentEntity){
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        BeanUtils.copyProperties(appointmentEntity, appointmentDTO);
        return appointmentDTO;
    }

    //convert DTO to entity
    private AppointmentEntity convertDTOToEntity(AppointmentDTO appointmentDTO){
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        BeanUtils.copyProperties(appointmentDTO, appointmentEntity);
        return appointmentEntity;
    }

    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        AppointmentEntity appointmentEntity = convertDTOToEntity(appointmentDTO);
        AppointmentEntity savedAppointment = appointmentRepository.save(appointmentEntity);
        return convertEntityToDTO(savedAppointment);
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public AppointmentDTO getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).map(this::convertEntityToDTO).orElse(null);
    }

    public AppointmentDTO updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) {
        if (appointmentRepository.existsById(appointmentId)) {
            AppointmentEntity appointmentEntity = convertDTOToEntity(appointmentDTO);
            appointmentEntity.setAppointmentId(appointmentId);
            AppointmentEntity updatedAppointment = appointmentRepository.save(appointmentEntity);
            return convertEntityToDTO(updatedAppointment);
        }
        return null;
    }

    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

//    private AppointmentDTO entityToDto(AppointmentEntity appointmentEntity) {
//        AppointmentDTO appointmentDTO = new AppointmentDTO();
//        appointmentDTO.setAppointmentId(appointmentEntity.getAppointmentId());
//        appointmentDTO.setAppointmentDate(appointmentEntity.getAppointmentDate());
//        appointmentDTO.setAppointmentTime(appointmentEntity.getAppointmentTime());
//        appointmentDTO.setStatus(appointmentEntity.getStatus());
//        appointmentDTO.setPatientId(appointmentEntity.getPatient().getPatientId());
//        appointmentDTO.setDoctorId(appointmentEntity.getDoctor().getDoctorId());
//        return appointmentDTO;
//    }
//
//    private AppointmentEntity dtoToEntity(AppointmentDTO appointmentDTO) {
//        AppointmentEntity appointmentEntity = new AppointmentEntity();
//        appointmentEntity.setAppointmentDate(appointmentDTO.getAppointmentDate());
//        appointmentEntity.setAppointmentTime(appointmentDTO.getAppointmentTime());
//        appointmentEntity.setStatus(appointmentDTO.getStatus());
//
//        PatientEntity patientEntity = patientRepository.findById(appointmentDTO.getPatientId()).orElse(null);
//        DoctorEntity doctorEntity = doctorRepository.findById(appointmentDTO.getDoctorId()).orElse(null);
//
//        appointmentEntity.setPatient(patientEntity);
//        appointmentEntity.setDoctor(doctorEntity);
//
//        return appointmentEntity;
//    }


}
