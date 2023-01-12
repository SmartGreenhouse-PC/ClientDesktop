package it.unibo.smartgh.controller;

import it.unibo.smartgh.view.ApplicationView;

public class ApplicationControllerImpl implements ApplicationController {

    private final ApplicationView view;

    public ApplicationControllerImpl(ApplicationView view) {
        this.view = view;
    }

    @Override
    public void start() {
        this.view.display();
    }
}
