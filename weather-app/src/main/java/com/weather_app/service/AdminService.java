package com.weather_app.service;

import com.weather_app.exception.EntityValidationException;
import com.weather_app.exception.ExceptionPayload;
import com.weather_app.model.Admin;
import com.weather_app.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService extends BasicServiceOperations<AdminRepository, Admin> {

    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository repository, PasswordEncoder passwordEncoder) {
        super(repository);
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Admin> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Override
    public Admin save(Admin entity) {
        if (entity.getId() == null) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }

        return super.save(entity);
    }

    public Admin login(String email, String password) {
        Admin admin = this.repository.findByEmail(email)
                .orElseThrow(() -> new EntityValidationException(ExceptionPayload.builder()
                        .code("WrongEmail")
                        .fieldName("email")
                        .rejectedValue(email)
                        .message("Wrong email")
                        .build())
                );
        if (!admin.getPassword().equals(password)) {
            throw new EntityValidationException(ExceptionPayload.builder()
                    .code("WrongPassword")
                    .fieldName("password")
                    .rejectedValue(password)
                    .message("Wrong password")
                    .build());
        }

        return admin;
    }
}