package it.unibo.smartgh.controller;

import it.unibo.smartgh.model.ParameterValue;
import it.unibo.smartgh.model.ParameterValueImpl;
import it.unibo.smartgh.view.ParameterPageView;
import it.unibo.smartgh.view.ParameterPageViewImpl;

import java.util.Date;

public class ParameterPageControllerImpl implements ParameterPageController {
    private String name = "temperature";
    private ParameterValue currentVal = new ParameterValueImpl(new Date(), 21.0);
    private Double min = 5.0;
    private Double max = 28.0;

    ParameterPageView view;

    public ParameterPageControllerImpl() {
        view = new ParameterPageViewImpl(name, min, max, currentVal.getValue());
    }

    @Override
    public ParameterPageView getView() {
        return this.view;
    }
}
