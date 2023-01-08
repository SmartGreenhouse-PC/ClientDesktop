package it.unibo.smartgh.view;

public interface ParameterPageView {
    /**
     * Set the current value label
     *
     * @param value
     */
    void setCurrentValue(Double value);

    /**
     * Initialize the parameter page
     *
     * @param name         of the parameter
     * @param min          optima value
     * @param max          optimal value
     * @param currentValue sensed by the microcontroller
     */
    void initializePage(String name, Double min, Double max, Double currentValue);
}
