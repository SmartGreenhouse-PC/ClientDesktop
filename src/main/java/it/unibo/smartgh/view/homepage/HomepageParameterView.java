package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.view.SubView;

/**
 * The view interface of the Homepage parameter page.
 */
public interface HomepageParameterView extends SubView {

    /**
     * Sets the current parameter.
     * @param parameter the parameter
     */
    void setParameter(ParameterType parameter);

    /**
     * Sets the current plant optimal value.
     * @param minValue the min value
     * @param maxValue the max value
     * @param unit     the unit of measurement
     */
    void setOptimalValue(Double minValue, Double maxValue, String unit);

    /**
     * Sets the parameter current value.
     * @param value  the parameter value
     * @param status the parameter status
     */
    void setCurrentValue(Double value, String status);

    /**
     * Gets the current parameter status.
     * @return the parameter status
     */
    String getParameterStatus();
}
