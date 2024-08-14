package com.healthcaremanagement.HealthcareManagement.Config;

import com.healthcaremanagement.HealthcareManagement.filter.JWTAuthenticationFilter;
import com.healthcaremanagement.HealthcareManagement.service.UserDetailsServiceImpel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpel userDetailsServiceImpl;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;


    public SecurityConfig(UserDetailsServiceImpel userDetailsService, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImpl = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(
                        req -> req.requestMatchers("login/**", "register/**")
                                .permitAll()
                                .requestMatchers("/admins/**","/patients/getall","/appointments/getall","/billing/**","/doctors/getall","/doctors/create").hasAuthority("ADMIN")
                                .requestMatchers("/patients/**","appointments/**","medicalRecords/getId","billing/getId").hasAuthority("PATIENT")
                                .requestMatchers("/doctors/**","appointments/getId","medicalRecords/**").hasAuthority("DOCTOR")
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImpl)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
