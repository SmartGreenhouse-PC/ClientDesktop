package it.unibo.smartgh.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class ApplicationViewImpl extends Application implements ApplicationView {

    @Override
    public void start(Stage stage) throws Exception {
        final double width = Screen.getPrimary().getBounds().getWidth() / 1.2;
        final double height = Screen.getPrimary().getBounds().getHeight() / 1.2;
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layout/base.fxml"));
        Scene scene = new Scene(root, width,height);
        stage.getIcons().add(new Image("images/smartgh.png"));
        stage.setTitle("Smart Greenhouse");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void display() {
        launch();
    }
}
