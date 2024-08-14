package com.healthcaremanagement.HealthcareManagement.controller;

import com.healthcaremanagement.HealthcareManagement.entity.AuthenticationResponse;
import com.healthcaremanagement.HealthcareManagement.entity.UserEntity;
import com.healthcaremanagement.HealthcareManagement.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


        @Autowired
        private AuthenticationService authenticationService;


        @PostMapping("/register")
        public ResponseEntity<AuthenticationResponse> register(@RequestBody UserEntity request){
            return ResponseEntity.ok(authenticationService.register(request));
        }

        @PostMapping("/login")
        public ResponseEntity<AuthenticationResponse> login (@RequestBody UserEntity request){
            return ResponseEntity.ok(authenticationService.authenticate(request));
        }


}
