package com.weather_app.payload;

import com.weather_app.model.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesViewDTO {
    private Long customerId;
    private String customerName;
    private List<City> favoriteCities;
}
