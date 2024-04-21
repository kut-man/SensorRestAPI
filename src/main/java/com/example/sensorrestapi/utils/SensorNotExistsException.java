package com.example.sensorrestapi.utils;

public class SensorNotExistsException extends RuntimeException{
    public SensorNotExistsException(String message) {
        super(message);
    }
}
