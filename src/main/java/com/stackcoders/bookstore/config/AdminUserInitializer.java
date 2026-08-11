package com.stackcoders.bookstore.config;

import com.stackcoders.bookstore.constants.Role;
import com.stackcoders.bookstore.entity.Admin;
import com.stackcoders.bookstore.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminUserInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String adminEmail = "admin@stackcoders.com";
        if (!adminRepository.existsByEmail(adminEmail)) {
            log.info("Creating default admin user in Admin table...");
            Admin adminUser = Admin.builder()
                    .username("Admin")
                    .email(adminEmail)
                    .password(passwordEncoder.encode("Admin@123"))
                    .role(Role.ADMIN)
                    .build();
            adminRepository.save(adminUser);
            log.info("Default admin user created successfully.");
        } else {
            log.info("Default admin user already exists.");
        }
    }
}
