package com.weather_app.service;

import com.weather_app.model.City;
import com.weather_app.model.Customer;
import com.weather_app.model.Favorites;
import com.weather_app.payload.FavoritesViewDTO;
import com.weather_app.repository.CityRepository;
import com.weather_app.repository.CustomerRepository;
import com.weather_app.repository.FavoritesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {

   private final FavoritesRepository favoritesRepository;
   private final CustomerRepository customerRepository;
   private final CityRepository cityRepository;

   public Favorites addFavorite(Long customerId, City city) {
      if (favoritesRepository.findByCustomerIdAndCityId(customerId, city.getId()).isPresent()) {
         throw new RuntimeException("City is already in favorites.");
      }

      Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
      cityRepository.save(city);

      Favorites favorite = Favorites.builder()
            .customer(customer)
            .city(city)
            .build();

      return favoritesRepository.save(favorite);
   }

   public void removeFavorite(Long customerId, City city) {
      Favorites favorite = favoritesRepository
            .findByCustomerIdAndCityId(customerId, city.getId())
            .orElseThrow(() -> new RuntimeException("Favorite not found"));
      favoritesRepository.delete(favorite);
   }

   public List<City> getFavoriteCities(Long customerId) {
      return favoritesRepository.findByCustomerId(customerId)
            .stream()
            .map(Favorites::getCity)
            .collect(Collectors.toList());
   }
   
   public List<FavoritesViewDTO> getFavoritesView() {
      List<Long> customerIds = favoritesRepository.findAll().stream()
            .map(favorite -> favorite.getCustomer().getId())
            .distinct()
            .collect(Collectors.toList());
      
      return customerIds.stream().map(customerId -> {
         Customer customer = customerRepository.findById(customerId)
               .orElseThrow(() -> new RuntimeException("Customer not found"));
         List<City> favoriteCities = getFavoriteCities(customerId);
         
         return FavoritesViewDTO.builder()
               .customerId(customerId)
               .customerName(customer.getFirstName() + " " + customer.getLastName())
               .favoriteCities(favoriteCities)
               .build();
      }).collect(Collectors.toList());
   }
   
   public FavoritesViewDTO getFavoritesViewByCustomerId(Long customerId) {
      Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
      List<City> favoriteCities = getFavoriteCities(customerId);
      
      return FavoritesViewDTO.builder()
            .customerId(customerId)
            .customerName(customer.getFirstName() + " " + customer.getLastName())
            .favoriteCities(favoriteCities)
            .build();
   }
}
