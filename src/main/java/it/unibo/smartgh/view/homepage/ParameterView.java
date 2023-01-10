package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.model.ParameterType;
import it.unibo.smartgh.view.SubView;

public interface ParameterView extends SubView {

    void setParameter(ParameterType parameter);

    void setOptimalValue(Double minValue, Double maxValue, String unit);
}
