package it.unibo.smartgh.model.plant;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implementation of the parameter entity.
 */
public class PlantParameterImpl implements PlantParameter {
    private final String name;
    private final Double min;
    private final Double max;
    private final String unit;

    /**
     * Constructor for a plant parameter.
     * @param name of the parameter
     * @param min of the parameter
     * @param max of the parameter
     * @param unit of the parameter
     */
    public PlantParameterImpl(String name, Double min, Double max, String unit) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.unit = new String(unit.getBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getMin() {
        return this.min;
    }

    @Override
    public Double getMax() {
        return this.max;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlantParameterImpl parameter = (PlantParameterImpl) o;
        return name.equals(parameter.name) && min.equals(parameter.min) && max.equals(parameter.max) && unit.equals(parameter.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, min, max, unit);
    }
}
