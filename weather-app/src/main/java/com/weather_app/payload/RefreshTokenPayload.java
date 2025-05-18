package com.weather_app.payload;

import lombok.Data;

@Data
public class RefreshTokenPayload {

    private String refreshToken;
}