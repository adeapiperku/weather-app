package com.weather_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weather_app.model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Long>{

    Optional<Admin> findByEmail(String email);
    
}
