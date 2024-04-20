package com.example.sensorrestapi.utils;

public class SensorExistsException extends RuntimeException{
    public SensorExistsException(String message) {
        super(message);
    }
}
