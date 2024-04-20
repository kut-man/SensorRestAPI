package com.example.sensorrestapi.repositories;

import com.example.sensorrestapi.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    Sensor findByName(String name);
}
