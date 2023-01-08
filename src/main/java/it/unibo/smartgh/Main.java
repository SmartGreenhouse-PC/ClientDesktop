package it.unibo.smartgh;

import it.unibo.smartgh.controller.ParameterPageController;
import it.unibo.smartgh.controller.ParameterPageControllerImpl;
import it.unibo.smartgh.view.ParameterPageViewImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int SCENE_WIDTH = 500;
    private static final int SCENE_HEIGHT = 300;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader firstPaneLoader = new FXMLLoader(ClassLoader.getSystemResource("layout/parameterPage.fxml"));
        ParameterPageController controller = new ParameterPageControllerImpl();

        firstPaneLoader.setController(controller.getView());

        Parent root = firstPaneLoader.load();

        final Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        // Stage configuration
        stage.setTitle("JavaFX - Complete Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
