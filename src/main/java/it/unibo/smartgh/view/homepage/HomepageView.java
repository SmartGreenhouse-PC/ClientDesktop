package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.view.ApplicationView;
import it.unibo.smartgh.view.SubView;

import java.util.List;

/**
 * The Homepage view interface.
 */
public interface HomepageView extends SubView {

    /**
     * Sets the plant information.
     * @param name        the name of the plant
     * @param description the description of the plant
     * @param img         the image path of the plant
     */
    void setPlantInformation(String name, String description, String img);

    /**
     * Sets the parameter information.
     * @param parameterType the parameter's type to set
     * @param minValue      the parameter's min value
     * @param maxValue      the parameter's max value
     * @param unit          the parameter's unit of measurement
     */
    void setParameterInfo(ParameterType parameterType, Double minValue, Double maxValue, String unit);

    /**
     * Update the given parameter values.
     *
     * @param parameterType the parameter type
     * @param value         the value of the parameter
     * @param status        the status of the parameter
     */
    void updateParameterValue(ParameterType parameterType, Double value, String status);

    /**
     * Initialize the greenhouses' combo box.
     * @param greenhousesId list of greenhouses id.
     */
    void initializeGreenhousesSelector(List<String> greenhousesId);
}
