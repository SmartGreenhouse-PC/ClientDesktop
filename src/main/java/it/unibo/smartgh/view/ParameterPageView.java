package it.unibo.smartgh.view;

import java.util.Map;

public interface ParameterPageView {
    /**
     * Set the current value label
     *
     * @param value
     * @param history parameter history
     * @param status parameter status
     */
    void updateValues(String value, String status, Map<String, Double> history);

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
