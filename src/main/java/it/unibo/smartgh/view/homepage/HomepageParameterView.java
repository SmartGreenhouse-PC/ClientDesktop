package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.view.SubView;

public interface HomepageParameterView extends SubView {

    void setParameter(ParameterType parameter);

    void setOptimalValue(Double minValue, Double maxValue, String unit);

    void setCurrentValue(Double value, String status);

    String getParameterStatus();
}