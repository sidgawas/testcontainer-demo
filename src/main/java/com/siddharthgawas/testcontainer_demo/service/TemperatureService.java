package com.siddharthgawas.testcontainer_demo.service;

import com.siddharthgawas.testcontainer_demo.dto.TemperatureReading;

/**
 * The interface Temperature service.
 */
public interface TemperatureService {

    /**
     * Record temperature.
     *
     * @param sensorId the sensor id
     * @param reading  the reading
     */
    void recordTemperature(String sensorId, TemperatureReading reading);

    /**
     * Average double.
     *
     * @param sensorId the sensor id
     * @return the double
     */
    Double average(String sensorId);
}
