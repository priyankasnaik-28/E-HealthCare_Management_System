package com.healthcaremanagement.HealthcareManagement.repository;

import com.healthcaremanagement.HealthcareManagement.entity.AdminEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testFindById() {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setName("John Doe");
        adminEntity.setEmail("john.doe@example.com");
        adminEntity.setPassword("password");
        AdminEntity savedAdmin = adminRepository.save(adminEntity);

        Optional<AdminEntity> foundAdmin = adminRepository.findById(savedAdmin.getAdminId());

        assertTrue(foundAdmin.isPresent());
        assertEquals(savedAdmin.getName(), foundAdmin.get().getName());
    }

    @Test
    public void testFindById_NotFound() {
        // Try to find an admin with an ID that does not exist
        Optional<AdminEntity> foundAdmin = adminRepository.findById(999L); // Assume 999 does not exist

        // Assert that no admin is found
        assertTrue(foundAdmin.isEmpty());
    }


    @Test
    public void testSave() {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setName("Jane Doe");
        adminEntity.setEmail("jane.doe@example.com");
        adminEntity.setPassword("password");

        AdminEntity savedAdmin = adminRepository.save(adminEntity);

        assertTrue(savedAdmin.getAdminId() != null); // Check if ID is generated
        assertEquals("Jane Doe", savedAdmin.getName());
    }



    @Test
    public void testSave_InvalidDataType() {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setName("Valid Name");
        adminEntity.setEmail("invalid-email"); // Assuming email validation is in place
        adminEntity.setPassword("password");

        // Expect a persistence exception or a custom validation exception if email is invalid
        try{
            adminRepository.save(adminEntity);
        }catch (Exception e){
            throw new RuntimeException("cant save");
        }
    }


    @Test
    public void testDeleteById() {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setName("Alice Smith");
        adminEntity.setEmail("alice.smith@example.com");
        adminEntity.setPassword("password");
        AdminEntity savedAdmin = adminRepository.save(adminEntity);

        adminRepository.deleteById(savedAdmin.getAdminId());

        Optional<AdminEntity> deletedAdmin = adminRepository.findById(savedAdmin.getAdminId());

        assertTrue(deletedAdmin.isEmpty()); // Ensure the admin is deleted
    }

    @Test
    public void testDeleteById_NotFound() {
        // Ensure the repository does not throw an exception when deleting a non-existent ID
        try {
            adminRepository.deleteById(999L); // Assume 999 does not exist
            // No exception should be thrown
        } catch (Exception e) {
            // If an exception is thrown, fail the test
            fail("Exception should not be thrown when deleting a non-existent entity");
        }
    }


    @Test
    public void testExistsById() {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setName("Bob Johnson");
        adminEntity.setEmail("bob.johnson@example.com");
        adminEntity.setPassword("password");
        AdminEntity savedAdmin = adminRepository.save(adminEntity);

        boolean exists = adminRepository.existsById(savedAdmin.getAdminId());

        assertTrue(exists);

        adminRepository.deleteById(savedAdmin.getAdminId());

        exists = adminRepository.existsById(savedAdmin.getAdminId());

        assertFalse(exists); // Ensure that the admin no longer exists
    }

    @Test
    public void testExistsById_NotFound() {
        // Check if an admin with a non-existent ID exists
        boolean exists = adminRepository.existsById(999L); // Assume 999 does not exist
        // Assert that the ID does not exist
        assertFalse(exists);
    }



}
