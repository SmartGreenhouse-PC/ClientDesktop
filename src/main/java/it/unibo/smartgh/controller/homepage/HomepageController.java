package it.unibo.smartgh.controller.homepage;

/**
 * An interface that represents the controller of the application homepage.
 */
public interface HomepageController {

    /**
     * Initialize the homepage data.
     */
    void initializeData();

    /**
     * Close the socket.
     */
    void closeSocket();

    /**
     * Update the view according to the selected greenhouse.
     * @param id of the selected greenhouse.
     */
    void greenhouseSelected(String id);
}
