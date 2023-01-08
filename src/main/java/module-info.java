module it.unibo.smartgh.view {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unibo.smartgh.view to javafx.fxml;
    exports it.unibo.smartgh.view;
}