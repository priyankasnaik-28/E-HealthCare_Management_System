package com.healthcaremanagement.HealthcareManagement.entity;

import com.healthcaremanagement.HealthcareManagement.role.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

    @Data
    @Table(name = "user")
    @Entity
    public class UserEntity implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "lastName")
        private String lastName;

        @Column(name = "userName")
        private String userName;

        @Column(name = "password")
        private String password;

        @Enumerated(value = EnumType.STRING)
        private Role role;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(role.name()));
        }

        @Override
        public String getUsername() {
            return this.userName;
        }


        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }


