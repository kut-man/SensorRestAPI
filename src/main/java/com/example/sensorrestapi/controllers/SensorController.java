package com.example.sensorrestapi.controllers;

import com.example.sensorrestapi.dto.SensorDTO;
import com.example.sensorrestapi.models.Sensor;
import com.example.sensorrestapi.services.SensorService;
import com.example.sensorrestapi.utils.ErrorMessageBuilder;
import com.example.sensorrestapi.utils.ErrorResponse;
import com.example.sensorrestapi.utils.SensorExistsException;
import com.example.sensorrestapi.utils.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorValidator sensorValidator;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorValidator sensorValidator, SensorService sensorService, ModelMapper modelMapper) {
        this.sensorValidator = sensorValidator;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new SensorExistsException(ErrorMessageBuilder.buildErrorMessage(bindingResult));
        }

        sensorService.saveSensor(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorExistsException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
