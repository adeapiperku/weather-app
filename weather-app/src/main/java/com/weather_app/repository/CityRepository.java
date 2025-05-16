package com.weather_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.weather_app.model.City;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
   Optional<City> findByCityName(String cityName);
}
