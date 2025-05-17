package com.weather_app.repository;

import com.weather_app.model.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
   List<Favorites> findByCustomerId(Long customerId);

   Optional<Favorites> findByCustomerIdAndCityId(Long customerId, Long cityId);
}
