package it.unibo.smartgh.entity.plant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.unibo.smartgh.model.plant.PlantParameter;
import it.unibo.smartgh.model.plant.PlantParameterBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test to verify the correct creation of a {@link PlantParameter} and its property.
 */
class ParameterTest {

    private static PlantParameter parameter;
    private static String name;
    private static Double min;
    private static Double max;

    private static String unit;

    @BeforeAll
    static void setUp() {
        name = "parameter test";
        min = 10.0;
        max = 100.0;
        unit = "mt";
        parameter = new PlantParameterBuilder(name).min(min).max(max).unit(unit).build();
    }

    @Test
    void testGetName() {
        assertEquals(name, parameter.getName());
    }

    @Test
    void testGetMin() {
        assertEquals(min, parameter.getMin());
    }

    @Test
    void testGetMax() {
        assertEquals(max, parameter.getMax());
    }

    @Test
    void testGetUnit() {
        assertEquals(unit, parameter.getUnit());
    }
}