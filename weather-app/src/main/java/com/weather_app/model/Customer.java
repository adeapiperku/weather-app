package com.weather_app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Customer extends UserAccount {

    @Transient
    private String type = "Customer";
}