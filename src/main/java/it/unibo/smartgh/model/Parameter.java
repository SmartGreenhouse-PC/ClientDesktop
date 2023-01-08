package it.unibo.smartgh.model;

import java.util.List;

/**
 * Interface that represent a parameter
 */
public interface Parameter {
    /**
     * Get the parameter name
     * @return the parameter name
     */
    String getName();

    /**
     * Get the parameter current value
     * @return the parameter current value
     */
    ParameterValue getCurrentValue();

    /**
     * Get the parameter history
     * @return the
     */
    List<ParameterValue> getHistory();
}
