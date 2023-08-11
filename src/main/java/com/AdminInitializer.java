package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.repo.AdminRepo;
import com.models.Admin;

@Component
public class AdminInitializer implements ApplicationRunner {

    @Autowired
    private AdminRepo adminRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if an admin already exists
        if (adminRepository.findByUsername("admin") == null) {
            // If not, create the initial admin
            Admin admin = new Admin();
            admin.setUsername("admin");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = passwordEncoder.encode("password");
            admin.setPassword(encryptedPassword); // Replace with a secure password
            //admin.setRole("ROLE_ADMIN");
            adminRepository.save(admin);
        }
    }
}
