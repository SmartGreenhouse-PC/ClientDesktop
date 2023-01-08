package it.unibo.smartgh.model;

import java.util.Date;

/**
 * Implementation of the parameter value
 */
public class ParameterValueImpl implements ParameterValue{
    private final Date date;
    private final Double value;

    /**
     * Constructor for the parameter value
     * @param date of the parameter value
     * @param value of the parameter
     */
    public ParameterValueImpl(Date date, Double value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public Double getValue() {
        return this.value;
    }
}
