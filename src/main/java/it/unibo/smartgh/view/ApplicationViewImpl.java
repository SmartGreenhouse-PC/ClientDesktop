package it.unibo.smartgh.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class ApplicationViewImpl extends Application implements ApplicationView {

    @FXML
    private BorderPane borderPane;

    @FXML
    public void initialize() {
        this.changeScene("homepage.fxml");
    }

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

    @Override
    public Optional<SubView> changeScene(String fxmlFileName) {
        try {
            final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/layout/" + fxmlFileName));
            final Parent scene = loader.load();
            final SubView view = loader.getController();
            view.setMainView(this);
            this.borderPane.setCenter(scene);
            BorderPane.setMargin(scene, new Insets(5, 20, 5, 20));
            return Optional.of(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
