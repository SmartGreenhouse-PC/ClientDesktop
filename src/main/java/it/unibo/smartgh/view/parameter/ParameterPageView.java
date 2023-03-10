package it.unibo.smartgh.view.parameter;

import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.view.SubView;

import java.util.Map;

/**
 * An interface that represents the view of parameter page.
 */
public interface ParameterPageView extends SubView {

    /**
     * Set the current value label.
     * @param value     parameter value
     * @param history   parameter history
     * @param status    parameter status
     */
    void updateValues(String value, String status, Map<String, Double> history);

    /**
     * Set the current parameter.
     * @param parameter the parameter to set
     */
    void setParameter(ParameterType parameter);

    /**
     * Initialize the parameter page
     *
     * @param parameter     the parameter type
     * @param min           optima value
     * @param max           optimal value
     * @param currentValue  sensed by the microcontroller
     * @param history       parameter history
     * @param status        parameter status
     */
    void initializePage(ParameterType parameter, String min, String max, String currentValue, Map<String, Double> history, String status);
}
