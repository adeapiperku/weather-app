package com.weather_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.weather_app.model.City;
import com.weather_app.service.CityService;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

   private final CityService cityService;

   @PostMapping
   public ResponseEntity<City> createCity(@RequestBody City city) {
      return ResponseEntity.ok(cityService.createCity(city));
   }

   @GetMapping
   public ResponseEntity<List<City>> getAllCities() {
      return ResponseEntity.ok(cityService.getAllCities());
   }

   @GetMapping("/{id}")
   public ResponseEntity<City> getCityById(@PathVariable Long id) {
      return cityService.getCityById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
      cityService.deleteCity(id);
      return ResponseEntity.noContent().build();
   }
}