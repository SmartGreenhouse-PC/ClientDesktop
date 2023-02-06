package it.unibo.smartgh.model.parameter;

import java.util.Date;

/**
 * Interface that represent a parameter value
 */
public interface ParameterValue {

    /**
     * Get the greenhouse id
     * @return the greenhouse id
     */
    String getGreenhouseId();

    /**
     * Get the date when the value is sensed
     * @return the date when the value is sensed
     */
    Date getDate();

    /**
     * Get the plant value
     * @return the plant value
     */
    Double getValue();

}
