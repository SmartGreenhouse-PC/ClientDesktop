package it.unibo.smartgh.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
     * @return the history
     */
    List<ParameterValue> getHistory();

    /**
     * Set the parameter history with a limit of 20 data
     * @param history of the parameter values
     */
    void setHistory(List<ParameterValue> history);
    /**
     * Get the parameter history as map (date, value)
     * @return the history as map
     */
    Map<String, Double> getHistoryAsMap();
}
