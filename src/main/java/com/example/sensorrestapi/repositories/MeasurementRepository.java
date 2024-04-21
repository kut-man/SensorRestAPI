package com.example.sensorrestapi.repositories;

import com.example.sensorrestapi.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    @Query(value = "SELECT COUNT(DISTINCT (DATE_TRUNC('day', takenAt)) ) FROM Measurement WHERE isRaining = true")
    long countDaysWithRain();
}
