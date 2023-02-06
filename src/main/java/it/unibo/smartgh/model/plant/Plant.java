package it.unibo.smartgh.model.plant;

import it.unibo.smartgh.model.parameter.ParameterType;

import java.util.Map;

/**
 * The Plant entity interface.
 */
public interface Plant {
    /**
     * The plant name.
     * @return the plant name.*/
    String getName();
    /**
     * The plant description.
     * @return the plant description.*/
    String getDescription();
    /**
     * Get the plant image link.
     * @return the plant image link.
     */
    String getImg();

    /**
     * Get the map of parameters ParameterType, Parameter.
     * @return the map of parameters ParameterType, Parameter.
     */
    Map<ParameterType, PlantParameter> getParameters();

}
