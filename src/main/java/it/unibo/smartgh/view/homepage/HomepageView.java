package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.model.ParameterType;
import it.unibo.smartgh.view.SubView;

public interface HomepageView extends SubView {

    void setPlantInformation(String name, String description, String img);

    void setParameterInfo(ParameterType parameterType, Double minValue, Double maxValue, String unit);
}
