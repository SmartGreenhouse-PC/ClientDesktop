package it.unibo.smartgh.controller;


import it.unibo.smartgh.view.OperationPageView;

import java.time.LocalDate;

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

    /**
     * Update the view according to the selected date range
     * @param from date
     * @param to date
     */
    void selectRange(LocalDate from, LocalDate to);
}
