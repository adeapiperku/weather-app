package com.weather_app.controller;

import com.weather_app.model.City;
import com.weather_app.model.Favorites;
import com.weather_app.payload.FavoritesViewDTO;
import com.weather_app.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoritesController {

   private final FavoritesService favoritesService;

   @PostMapping()
   public ResponseEntity<Favorites> addFavorite(@RequestParam Long customerId, @RequestBody City city) {
      return ResponseEntity.ok(favoritesService.addFavorite(customerId, city));
   }

   @DeleteMapping
   public ResponseEntity<Void> removeFavorite(@RequestParam Long customerId, @RequestBody City city) {
      favoritesService.removeFavorite(customerId, city);
      return ResponseEntity.ok().build();
   }

   @GetMapping("/{customerId}")
   public ResponseEntity<List<City>> getFavoriteCities(@PathVariable Long customerId) {
      return ResponseEntity.ok(favoritesService.getFavoriteCities(customerId));
   }
   
   @GetMapping("/view")
   public ResponseEntity<List<FavoritesViewDTO>> getFavoritesView() {
      return ResponseEntity.ok(favoritesService.getFavoritesView());
   }
   
   @GetMapping("/view/{customerId}")
   public ResponseEntity<FavoritesViewDTO> getFavoritesViewByCustomerId(@PathVariable Long customerId) {
      return ResponseEntity.ok(favoritesService.getFavoritesViewByCustomerId(customerId));
   }
}
