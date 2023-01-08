package it.unibo.smartgh.model;

import java.util.Date;

/**
 * Interface that represent a parameter value
 */
public interface ParameterValue {
    /**
     * Get the parameter value date
     * @return the parameter value date
     */
    Date getDate();
    /**
     * Get the parameter value
     * @return the parameter value
     */
    Double getValue();
}
