package com.example.sensorrestapi.services;

import com.example.sensorrestapi.models.Measurement;
import com.example.sensorrestapi.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public void saveMeasurement(Measurement measurement) {
        measurement.setTakenAt(new Date());
        measurement.setSensor(sensorService.findSensor(measurement.getSensor().getName()).get());
        measurementRepository.save(measurement);
    }

    public List<Measurement> getMeasurement() {
        return measurementRepository.findAll();
    }
}
