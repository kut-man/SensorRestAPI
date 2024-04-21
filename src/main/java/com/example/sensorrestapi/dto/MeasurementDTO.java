package com.example.sensorrestapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {
    @NotNull
    @Max(value = 100, message = "Measurement value must be less than 100!")
    @Min(value = -100, message = "Measurement value must be more than -100!")
    private int value;

    @NotNull
    private boolean isRaining;

    @NotNull
    private SensorDTO sensor;

    public MeasurementDTO(int value, boolean isRaining, SensorDTO sensor) {
        this.value = value;
        this.isRaining = isRaining;
        this.sensor = sensor;
    }

    public MeasurementDTO() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isRaining() {
        return isRaining;
    }

    public void setRaining(boolean raining) {
        isRaining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
