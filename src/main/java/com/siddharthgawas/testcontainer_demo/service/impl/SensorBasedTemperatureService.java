package com.siddharthgawas.testcontainer_demo.service.impl;

import com.siddharthgawas.testcontainer_demo.dto.TemperatureReading;
import com.siddharthgawas.testcontainer_demo.entity.Sensor;
import com.siddharthgawas.testcontainer_demo.repository.SensorRepository;
import com.siddharthgawas.testcontainer_demo.service.TemperatureService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

/**
 * The type Sensor based temperature service.
 */
@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SensorBasedTemperatureService implements TemperatureService {

    /**
     * The Repository.
     */
    private final SensorRepository repository;

    /**
     * Record temperature.
     *
     * @param sensorId the sensor id
     * @param reading  the reading
     */
    @Override
    public void recordTemperature(final String sensorId, final TemperatureReading reading) {
        final var sensorOptional = repository.findBySensorId(sensorId);
        var sensor = Sensor.builder().sensorId(sensorId).build();
        if (sensorOptional.isPresent()) {
            sensor = sensorOptional.get();
        }
        if (CollectionUtils.isEmpty(sensor.getTemperatureRecords())) {
            sensor.setTemperatureRecords(new ArrayList<>());
        }
        sensor.getTemperatureRecords().add(reading.getValue());
        repository.save(sensor);
    }

    /**
     * Average double.
     *
     * @param sensorId the sensor id
     * @return the double
     */
    @Override
    public Double average(final String sensorId) {
        final var sensorOptional = repository.findBySensorId(sensorId);
        if (sensorOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sensor does not exist");
        }
        var average = 0.0;
        if (!CollectionUtils.isEmpty(sensorOptional.get().getTemperatureRecords())) {
            final var count = sensorOptional.get().getTemperatureRecords().size();
            average = sensorOptional.get().getTemperatureRecords().stream().reduce(average, (a, b) -> a + b) /  count;
        }
        return average;
    }
}
