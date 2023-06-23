package com.siddharthgawas.testcontainer_demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddharthgawas.testcontainer_demo.container.MongodbContainer;
import com.siddharthgawas.testcontainer_demo.dto.TemperatureReading;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.Stream;

/**
 * The type Test container demo application tests.
 */
@Testcontainers
@SpringBootTest
@ActiveProfiles(value = {"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestContainerDemoApplicationTests {

    /**
     * The constant MONGO_DB.
     */
    @Container
    protected static MongodbContainer MONGO_DB = MongodbContainer.getInstance();

    /**
     * The Mock mvc.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * The Object mapper.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Sensor values provider stream.
     *
     * @return the stream
     */
    private static Stream<Arguments> sensorValuesProvider() {
        return Stream.of(
                Arguments.of("sensor01", 10.0),
                Arguments.of("sensor01", 20.0),
                Arguments.of("sensor02", 30.0)
        );
    }

    /**
     * Sensor expected average provider stream.
     *
     * @return the stream
     */
    private static Stream<Arguments> sensorExpectedAverageProvider() {
        return Stream.of(
                Arguments.of("sensor01", 15.0),
                Arguments.of("sensor02", 30.0)
        );
    }

    /**
     * Context loads.
     */
    @Test
    @Order(1)
    public void contextLoads() {
    }

    /**
     * Test when temperatures are recorded then return ok.
     *
     * @param sensorId    the sensor id
     * @param temperature the temperature
     * @throws Exception the exception
     */
    @ParameterizedTest
    @MethodSource("sensorValuesProvider")
    @Order(2)
    public void testWhenTemperaturesAreRecordedThenReturnOk(final String sensorId, Double temperature)
            throws Exception {
        final var request = TemperatureReading.builder().value(temperature).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sensor/{sensorId}/temperature", sensorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    /**
     * Test averages when sensor records are present.
     *
     * @param sensorId        the sensor id
     * @param expectedAverage the expected average
     * @throws Exception the exception
     */
    @ParameterizedTest
    @MethodSource("sensorExpectedAverageProvider")
    @Order(2)
    public void testAveragesWhenSensorRecordsArePresent(final String sensorId, Double expectedAverage)
            throws Exception {
        final var result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/sensor/{sensorId}/average", sensorId))
                .andDo(MockMvcResultHandlers.log()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        final var averageReading =
                objectMapper.readValue(result.getResponse().getContentAsString(), TemperatureReading.class);
        Assertions.assertEquals(expectedAverage, averageReading.getValue());
    }
}
