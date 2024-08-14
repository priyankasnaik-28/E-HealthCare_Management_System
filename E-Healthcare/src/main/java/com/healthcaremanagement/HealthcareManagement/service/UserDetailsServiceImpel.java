package com.healthcaremanagement.HealthcareManagement.service;


import com.healthcaremanagement.HealthcareManagement.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpel implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByuserName(username).orElseThrow(() ->new UsernameNotFoundException("User not found with this "+username));
    }
}

