package com.example.sensorrestapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotNull
    @Max(value = 100, message = "Measurement value must be less than 100!")
    @Min(value = -100, message = "Measurement value must be more than -100!")
    private int value;

    @Column(name = "isRaining")
    @NotNull
    private boolean isRaining;

    @ManyToOne
    @JoinColumn(name = "sensorId", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name = "takenAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    public Measurement(int id, int value, boolean isRaining, Sensor sensor, Date takenAt) {
        this.id = id;
        this.value = value;
        this.isRaining = isRaining;
        this.sensor = sensor;
        this.takenAt = takenAt;
    }

    public Measurement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }
}
