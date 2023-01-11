package it.unibo.smartgh.model;

import java.util.Date;

/**
 * This interface represents an operation in a greenhouse. An operation has a greenhouse id, a modality (automatic or manual),
 * a date, a parameter, an action and an optional value.
 */
public interface Operation {

    /**
     * Returns the id of the greenhouse where the operation took place.
     * @return the id of the greenhouse
     */
    String getGreenhouseId();

    /**
     * Sets the id of the greenhouse where the operation took place.
     * @param greenhouseId the id of the greenhouse
     */
    void setGreenhouseId(String greenhouseId);

    /**
     * Returns the modality of the operation (automatic or manual).
     * @return the modality of the operation
     */
    Modality getModality();

    /**
     * Sets the modality of the operation (automatic or manual).
     * @param modality the modality of the operation
     */
    void setModality(Modality modality);

    /**
     * Returns the date of the operation.
     * @return the date of the operation
     */
    Date getDate();

    /**
     * Sets the date of the operation.
     * @param date the date of the operation
     */
    void setDate(Date date);

    /**
     * Returns the parameter of the operation.
     * @return the parameter of the operation
     */
    String getParameter();

    /**
     * Sets the parameter of the operation.
     * @param parameter the parameter of the operation
     */
    void setParameter(String parameter);

    /**
     * Returns the action of the operation.
     * @return the action of the operation
     */
    String getAction();

    /**
     * Sets the action of the operation.
     * @param action the action of the operation
     */
    void setAction(String action);

}
