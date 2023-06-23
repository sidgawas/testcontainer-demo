package com.siddharthgawas.testcontainer_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Temperature reading.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureReading {
    /**
     * The Value.
     */
    private Double value;
}
