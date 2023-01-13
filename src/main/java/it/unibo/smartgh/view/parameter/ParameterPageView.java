package it.unibo.smartgh.view.parameter;

import it.unibo.smartgh.view.SubView;

import java.util.Map;

public interface ParameterPageView extends SubView {
    /**
     * Set the current value label
     *
     * @param value
     * @param history parameter history
     * @param status parameter status
     */
    void updateValues(String value, String status, Map<String, Double> history);

    void setParameter(String parameter);

    /**
     * Initialize the parameter page
     *
     * @param name         of the parameter
     * @param min          optima value
     * @param max          optimal value
     * @param currentValue sensed by the microcontroller
     * @param history parameter history
     * @param status parameter status
     */
    void initializePage(String name, String min, String max, String currentValue, Map<String, Double> history, String status);
}