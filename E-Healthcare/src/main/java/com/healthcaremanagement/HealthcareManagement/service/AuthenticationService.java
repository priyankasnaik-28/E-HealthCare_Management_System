package com.healthcaremanagement.HealthcareManagement.service;

import com.healthcaremanagement.HealthcareManagement.entity.AuthenticationResponse;
import com.healthcaremanagement.HealthcareManagement.entity.UserEntity;
import com.healthcaremanagement.HealthcareManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {


        @Autowired
        private UserRepository userRepository;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private JwtService jwtService;
        @Autowired
        private AuthenticationManager authenticationManager;

        public AuthenticationResponse register(UserEntity request){
            UserEntity user = new UserEntity();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUserName(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            user.setRole(request.getRole());

            user = userRepository.save(user);

            String token = jwtService.generateToken(user);
            return new AuthenticationResponse(token);
        }


        public AuthenticationResponse authenticate(UserDetails request){
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
            UserEntity user = userRepository.findByuserName(request.getUsername()).orElseThrow();
            String token = jwtService.generateToken(user);

            return new AuthenticationResponse(token);
        }
    }


