package it.unibo.smartgh.view;

import java.util.List;

public interface OperationPageView {
    /**
     * Insert a new record into the table
     * @param date of the operation
     * @param modality of the greenhouse during the operation
     * @param parameter on which the operation is performed
     * @param action performed
     */
    void addTableRow(String date, String modality, String parameter, String action);

    /**
     * Initialize view
     */
    void initializeView(List<String> parameters);
    /**
     * Clear the table content
     */
    void clearTable();
}
