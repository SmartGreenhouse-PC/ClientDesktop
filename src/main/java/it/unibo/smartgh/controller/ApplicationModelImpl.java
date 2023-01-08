package it.unibo.smartgh.controller;

import it.unibo.smartgh.model.ApplicationModel;

public class ApplicationModelImpl implements ApplicationModel {

    private final ApplicationController controller;

    public ApplicationModelImpl(ApplicationController applicationController) {
        this.controller = applicationController;
    }
}
