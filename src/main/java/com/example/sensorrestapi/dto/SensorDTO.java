package com.example.sensorrestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty
    @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters!")
    String name;

    public SensorDTO(String name) {
        this.name = name;
    }

    public SensorDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
