package it.unibo.smartgh.controller.parameter;

import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.view.parameter.ParameterPageView;

/**
 * Interface that represent the parameter page controller.
 */
public interface ParameterPageController {

    /**
     * Get the parameter page view.
     * @return the parameter page view
     */
    ParameterPageView getView();

    /**
     * Set the current parameter.
     * @param parameter the current parameter
     */
    void setParameter(ParameterType parameter);

    /**
     * Close the socket.
     */
    void closeSocket();
}
