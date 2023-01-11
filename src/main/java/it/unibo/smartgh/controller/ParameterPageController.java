package it.unibo.smartgh.controller;

import io.vertx.core.Future;
import it.unibo.smartgh.view.ParameterPageView;

public interface ParameterPageController {
    /**
     * Get the parameter page view
     * @return the parameter page view
     */
    ParameterPageView getView();
}
