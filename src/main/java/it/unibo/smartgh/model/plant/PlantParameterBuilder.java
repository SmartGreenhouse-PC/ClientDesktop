package it.unibo.smartgh.model.plant;

/**
 * Builder for the parameter entity.
 */
public class PlantParameterBuilder {
    private final String name;
    private Double min;
    private Double max;
    private String unit;

    /**
     * Constructor for the parameter builder.
     * @param name of the parameter
     */
    public PlantParameterBuilder(String name) {
        this.name = name;
    }

    /**
     * Parameter min value.
     * @param min of the parameter.
     * @return the builder.
     */
    public PlantParameterBuilder min(Double min){
        this.min = min;
        return this;
    }

    /**
     * Parameter max value.
     * @param max of the parameter.
     * @return the builder.
     */
    public PlantParameterBuilder max(Double max){
        this.max = max;
        return this;
    }

    /**
     * Parameter unit.
     * @param unit of the parameter.
     * @return the builder.
     */
    public PlantParameterBuilder unit(String unit){
        this.unit = unit;
        return this;
    }

    /**
     * Create a new Parameter object
     * @return a new plant parameter
     */
    public PlantParameter build(){
        return new PlantParameterImpl(name, min, max, unit);
    }
}
