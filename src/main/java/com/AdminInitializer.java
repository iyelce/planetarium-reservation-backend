package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.repo.AdminRepo;
import com.models.Admin;

// Uygulama ilk defa çalistiginda default admin yarat
@Component
public class AdminInitializer implements ApplicationRunner {

    @Autowired
    private AdminRepo adminRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // DB de admin var mı kontrol et
        if (adminRepository.findByUsername("admin") == null) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // TODO: güvenli sifre!!!!!!
            String encryptedPassword = passwordEncoder.encode("password");
            admin.setPassword(encryptedPassword);
            admin.setRole("ROLE_ADMIN");
            adminRepository.save(admin);
        }
    }
}
