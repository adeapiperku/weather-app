package com.weather_app.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City {
   // Id will be populated by Online Weather API
   @Id
   private Long id;

   @Column(nullable = false)
   private String cityName;
   @Column(name = "weather_condition")
   private String condition;

   private String description;
   private Double temperature;
   @Column(name = "timestamp") // Optional: maps to DB column named "timestamp"
   private LocalDate timestamp;
}
