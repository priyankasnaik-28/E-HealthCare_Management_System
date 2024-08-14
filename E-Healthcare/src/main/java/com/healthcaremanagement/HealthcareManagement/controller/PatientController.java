package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.PatientDTO;
import com.healthcaremanagement.HealthcareManagement.entity.PatientEntity;
import com.healthcaremanagement.HealthcareManagement.exception.PatientNotFoundException;
import com.healthcaremanagement.HealthcareManagement.exception.SaveErrorException;
import com.healthcaremanagement.HealthcareManagement.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    //method to signUp user using UserDTO class

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody PatientDTO patientDTO) {
        try {
            patientService.signUp(patientDTO);
            return new ResponseEntity<>(patientDTO, HttpStatus.OK);
        } catch (SaveErrorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //method to login user using UserDTO class
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody PatientDTO patientDTO) {
        boolean loginSuccess = patientService.login(patientDTO);
        if (loginSuccess) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }

    @GetMapping("/getall")
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }


    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable(value = "id") Long patientId) {
        try {
            PatientDTO patientDTO = patientService.getPatientById(patientId);
            return ResponseEntity.ok(patientDTO);
        } catch (PatientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@Valid @PathVariable(value = "id") Long patientId, @RequestBody PatientDTO patientDetails) {
        PatientDTO updatedPatient = patientService.updatePatient(patientId, patientDetails);
        if (updatedPatient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable(value = "id") Long patientId) {
        try {
            patientService.deletePatient(patientId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
