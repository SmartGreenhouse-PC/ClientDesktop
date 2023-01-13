package it.unibo.smartgh.controller;

import it.unibo.smartgh.view.ApplicationView;

/**
 * The implementation of {@link ApplicationController} interface.
 */
public class ApplicationControllerImpl implements ApplicationController {

    private final ApplicationView view;

    /**
     * Instantiates a new Application controller.
     * @param view the application view
     */
    public ApplicationControllerImpl(ApplicationView view) {
        this.view = view;
    }

    @Override
    public void start() {
        this.view.display();
    }
}
