package it.unibo.smartgh.model;

/**
 * The Plant entity interface
 */
public interface Plant {
    /**
     * The plant name.
     * @return the plant name*/
    String getName();
    /**
     * The plant description.
     * @return the plant description*/
    String getDescription();
    /**
     * Represent the lower bound of the air temperature parameter.
     * @return the lower bound of air temperature
     */
    Double getMinTemperature();
    /**
     * Represent the upper bound of the air temperature parameter.
     * @return the upper bound of air temperature
     */
    Double getMaxTemperature();
    /**
     * Represent the lower bound of the brightness parameter.
     * @return the lower bound of brightness
     */
    Double getMinBrightness();
    /**
     * Represent the upper bound of the brightness parameter.
     * @return the upper bound of brightness
     */
    Double getMaxBrightness();
    /**
     * Represent the lower bound of the soil humidity parameter.
     * @return the lower bound of soil humidity
     */
    Double getMinSoilMoisture();
    /**
     * Represent the upper bound of the soil humidity parameter.
     * @return the upper bound of soil humidity
     */
    Double getMaxSoilMoisture();
    /**
     * Represent the lower bound of the air humidity parameter.
     * @return the lower bound of air humidity
     */
    Double getMinHumidity();
    /**
     * Represent the upper bound of the air humidity parameter.
     * @return the upper bound of air humidity
     */
    Double getMaxHumidity();

    /**
     * Get the plant image link
     * @return the plant image link
     */
    String getImg();

}
