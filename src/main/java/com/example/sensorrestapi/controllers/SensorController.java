package com.example.sensorrestapi.controllers;

import com.example.sensorrestapi.dto.SensorDTO;
import com.example.sensorrestapi.models.Sensor;
import com.example.sensorrestapi.services.SensorService;
import com.example.sensorrestapi.utils.SensorErrorResponse;
import com.example.sensorrestapi.utils.SensorExistsException;
import com.example.sensorrestapi.utils.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorValidator sensorValidator;
    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorValidator sensorValidator, SensorService sensorService) {
        this.sensorValidator = sensorValidator;
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = convertToSensor(sensorDTO);
        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

            for(FieldError error : fieldErrorList) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorExistsException(errorMessage.toString());
        }

        sensorService.saveSensor(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorExistsException e) {
        SensorErrorResponse response = new SensorErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
