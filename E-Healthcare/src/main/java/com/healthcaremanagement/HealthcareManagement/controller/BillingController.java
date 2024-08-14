package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.dto.BillingDTO;
import com.healthcaremanagement.HealthcareManagement.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/create")
    public ResponseEntity<BillingDTO> createBilling(@RequestBody BillingDTO billingDTO) {
        return ResponseEntity.ok(billingService.createBilling(billingDTO));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<BillingDTO>> getAllBillings() {
        return ResponseEntity.ok(billingService.getAllBillings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingDTO> getBillingById(@PathVariable Long id) {
        BillingDTO billingDTO = billingService.getBillingById(id);
        if (billingDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(billingDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BillingDTO> updateBilling(@PathVariable Long id, @RequestBody BillingDTO billingDTO) {
        BillingDTO updatedBillingDTO = billingService.updateBilling(id, billingDTO);
        if (updatedBillingDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedBillingDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBilling(@PathVariable Long id) {
        billingService.deleteBilling(id);
        return ResponseEntity.ok().build();
    }
}
