package it.unibo.smartgh.model;

import java.util.Date;
import java.util.Objects;

/**
 * Implementation of the parameter value
 */
public class ParameterValueImpl implements ParameterValue{
    private String greenhouseId;
    private Date date;
    private Double value;

    /**
     * Empty constructor of plant value
     */
    public ParameterValueImpl() {}

    /**
     * Constructor of plant value
     * @param greenhouseId id of the greenhouse
     * @param date when the value is sensed
     * @param value sensed
     */
    public ParameterValueImpl(String greenhouseId, Date date, Double value) {
        this.greenhouseId = greenhouseId;
        this.date = date;
        this.value = value;
    }

    @Override
    public String getGreenhouseId() {
        return greenhouseId;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Double getValue() {
        return value;
    }


    @Override
    public void setGreenhouseId(String greenhouseId){
        this.greenhouseId = greenhouseId;
    }

    @Override
    public void setDate(Date date){
        this.date = date;
    }

    @Override
    public void setValue(Double value){
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParameterValueImpl that = (ParameterValueImpl) o;
        return Objects.equals(greenhouseId, that.greenhouseId) && Objects.equals(date, that.date) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(greenhouseId, date, value);
    }

    @Override
    public String toString() {
        return "PlantValue{" +
                "greenhouseId='" + greenhouseId + '\'' +
                ", date=" + date +
                ", value=" + value +
                '}';
    }
}
