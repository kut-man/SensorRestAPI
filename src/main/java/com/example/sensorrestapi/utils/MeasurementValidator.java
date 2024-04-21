package com.example.sensorrestapi.utils;

import com.example.sensorrestapi.models.Measurement;
import com.example.sensorrestapi.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        if (sensorService.findSensor(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "409", "Sensor name is invalid!");
        }
    }
}
