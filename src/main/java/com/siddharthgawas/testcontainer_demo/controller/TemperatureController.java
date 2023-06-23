package com.siddharthgawas.testcontainer_demo.controller;

import com.siddharthgawas.testcontainer_demo.dto.TemperatureReading;
import com.siddharthgawas.testcontainer_demo.service.TemperatureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Temperature controller.
 */
@RestController
@RequestMapping("/api/v1/sensor")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class TemperatureController {

    /**
     * The Temperature service.
     */
    private final TemperatureService temperatureService;

    /**
     * Record temperature response entity.
     *
     * @param sensorId the sensor id
     * @param reading  the reading
     * @return the response entity
     */
    @PostMapping("/{sensorId}/temperature")
    public ResponseEntity<?> recordTemperature(@PathVariable("sensorId") final String sensorId,
                                               @RequestBody final TemperatureReading reading) {
        temperatureService.recordTemperature(sensorId, reading);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Gets weekly average.
     *
     * @param sensorId the sensor id
     * @return the weekly average
     */
    @GetMapping("/{sensorId}/average")
    public ResponseEntity<TemperatureReading> getWeeklyAverage(@PathVariable("sensorId") final String sensorId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(TemperatureReading.builder().value(temperatureService.average(sensorId)).build());
    }

}
