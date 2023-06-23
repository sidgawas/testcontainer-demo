package com.siddharthgawas.testcontainer_demo.repository;

import com.siddharthgawas.testcontainer_demo.entity.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * The interface Sensor repository.
 */
public interface SensorRepository extends MongoRepository<Sensor, String> {

    /**
     * Find by sensor id optional.
     *
     * @param sensorId the sensor id
     * @return the optional
     */
    Optional<Sensor> findBySensorId(final String sensorId);
}
