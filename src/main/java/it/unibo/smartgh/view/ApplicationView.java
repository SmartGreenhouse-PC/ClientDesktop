package it.unibo.smartgh.view;

import java.util.Optional;

/**
 * The interface Application view.
 */
public interface ApplicationView {

    /**
     * Display the scene.
     */
    void display();

    /**
     * Change the current scene.
     * @param fxmlFile the fxml file of the scene's layout
     * @return an optional that contains the changed sub view
     */
    Optional<SubView> changeScene(String fxmlFile);

    /**
     * Set the greenhouseId.
     * @param id of the greenhouse selected.
     */
    void setId(String id);
}
