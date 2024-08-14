package com.healthcaremanagement.HealthcareManagement.repository;

import com.healthcaremanagement.HealthcareManagement.entity.AppointmentEntity;
import com.healthcaremanagement.HealthcareManagement.entity.DoctorEntity;
import com.healthcaremanagement.HealthcareManagement.entity.PatientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Time;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    // Mock data for testing
    private PatientEntity createPatient() {
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("John Doe");
        // Set other necessary fields
        return patient;
    }

    private DoctorEntity createDoctor() {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Dr. Smith");
        // Set other necessary fields
        return doctor;
    }

    @Test
    public void testSaveAppointment() {
        PatientEntity patient = createPatient();
        DoctorEntity doctor = createDoctor();

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setAppointmentDate(new Date());
        appointment.setAppointmentTime(new Time(System.currentTimeMillis()));
        appointment.setStatus("Scheduled");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);

        assertNotNull(savedAppointment.getAppointmentId());
        assertEquals("Scheduled", savedAppointment.getStatus());
    }

    @Test
    public void testFindById() {
        PatientEntity patient = createPatient();
        DoctorEntity doctor = createDoctor();

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setAppointmentDate(new Date());
        appointment.setAppointmentTime(new Time(System.currentTimeMillis()));
        appointment.setStatus("Scheduled");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);
        Optional<AppointmentEntity> foundAppointment = appointmentRepository.findById(savedAppointment.getAppointmentId());

        assertTrue(foundAppointment.isPresent());
        assertEquals("Scheduled", foundAppointment.get().getStatus());
    }

    @Test
    public void testFindById_NotFound() {
        Optional<AppointmentEntity> foundAppointment = appointmentRepository.findById(999L);
        assertTrue(foundAppointment.isEmpty());
    }

    @Test
    public void testDeleteById() {
        PatientEntity patient = createPatient();
        DoctorEntity doctor = createDoctor();

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setAppointmentDate(new Date());
        appointment.setAppointmentTime(new Time(System.currentTimeMillis()));
        appointment.setStatus("Scheduled");
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        AppointmentEntity savedAppointment = appointmentRepository.save(appointment);
        appointmentRepository.deleteById(savedAppointment.getAppointmentId());

        Optional<AppointmentEntity> deletedAppointment = appointmentRepository.findById(savedAppointment.getAppointmentId());
        assertTrue(deletedAppointment.isEmpty());
    }

    @Test
    public void testDeleteById_NotFound() {
        try {
            appointmentRepository.deleteById(999L);
        } catch (Exception e) {
            fail("Exception should not be thrown when deleting a non-existent entity");
        }
    }

}
