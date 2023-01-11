package it.unibo.smartgh.controller;


import it.unibo.smartgh.view.OperationPageView;

public interface OperationPageController {
    /**
     * Get the parameter page view
     * @return the parameter page view
     */
    OperationPageView getView();

    /**
     * Update the view according to the selected parameter
     * @param parameter selected
     */
    void changeSelectedParameter(String parameter);
}
