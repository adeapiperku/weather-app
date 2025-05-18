// package com.weather_app.config;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.SerializationFeature;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
// import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration
// @RequiredArgsConstructor
// public class AppConfig {

//     @Bean
//     public PasswordEncoder encoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public ObjectMapper objectMapper() {
//         return new ObjectMapper()
//                 .registerModule(new JavaTimeModule())
//                 .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//     }

// }

