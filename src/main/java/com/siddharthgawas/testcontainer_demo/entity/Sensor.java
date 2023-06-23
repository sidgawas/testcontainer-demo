package com.siddharthgawas.testcontainer_demo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

/**
 * The type Sensor.
 */
@Document
@Data
@Builder
public class Sensor {

    /**
     * The Id.
     */
    @MongoId
    private String id;

    /**
     * The Sensor id.
     */
    private String sensorId;

    /**
     * The Temperature records.
     */
    private List<Double> temperatureRecords;
}
