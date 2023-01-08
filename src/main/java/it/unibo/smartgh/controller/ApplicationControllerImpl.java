package it.unibo.smartgh.controller;

import it.unibo.smartgh.model.ApplicationModel;
import it.unibo.smartgh.view.ApplicationView;

public class ApplicationControllerImpl implements ApplicationController {

    private final ApplicationView view;
    private final ApplicationModel model;

    public ApplicationControllerImpl(ApplicationView view) {
        this.view = view;
        this.model = new ApplicationModelImpl(this);
    }

    @Override
    public void start() {
        this.view.display();
    }
}
