package com.weather_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.weather_app.model.City;
import com.weather_app.repository.CityRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

   private final CityRepository cityRepository;

   public City createCity(City city) {
      return cityRepository.findByCityName(city.getCityName())
              .orElseGet(() -> cityRepository.save(city));
  }

   public List<City> getAllCities() {
      return cityRepository.findAll();
   }

   public Optional<City> getCityById(Long id) {
      return cityRepository.findById(id);
   }

   public void deleteCity(Long id) {
      cityRepository.deleteById(id);
   }
}
