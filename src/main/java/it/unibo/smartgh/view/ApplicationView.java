package it.unibo.smartgh.view;

import java.util.Optional;

public interface ApplicationView {

    void display();

    Optional<SubView> changeScene(String fxmlFile);
}
