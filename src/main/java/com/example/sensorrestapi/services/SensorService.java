package com.example.sensorrestapi.services;

import com.example.sensorrestapi.models.Sensor;
import com.example.sensorrestapi.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findSensor(String name) {
        return Optional.ofNullable(sensorRepository.findByName(name));
    }

    public void saveSensor(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
