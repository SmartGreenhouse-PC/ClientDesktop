package it.unibo.smartgh.controller.operation;

import it.unibo.smartgh.view.operation.OperationPageView;

import java.time.LocalDate;

/**
 * The interface of the operation page controller.
 */
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

    /**
     * Close the socket.
     */
    void closeSocket();
}
