package com.example.sensorrestapi.controllers;

import com.example.sensorrestapi.dto.MeasurementDTO;
import com.example.sensorrestapi.models.Measurement;
import com.example.sensorrestapi.services.MeasurementService;
import com.example.sensorrestapi.utils.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementValidator measurementValidator;
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementValidator measurementValidator, MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementValidator = measurementValidator;
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements() {
        List<MeasurementDTO> measurementDTOList = new ArrayList<>();

        for(Measurement measurement : measurementService.getMeasurement()) {
            measurementDTOList.add(modelMapper.map(measurement, MeasurementDTO.class));
        }
         return measurementDTOList;
    }

    @GetMapping("/rainyDaysCount")
    public Map<String, Long> getNumberOfRainyDays() {
        Map<String, Long> rainyDaysCount = new HashMap<>();
        rainyDaysCount.put("rainyDaysCount", measurementService.getNumberOfRainyDays());
        return rainyDaysCount;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new SensorNotExistsException(ErrorMessageBuilder.buildErrorMessage(bindingResult));
        }
        measurementService.saveMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotExistsException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
