package it.unibo.smartgh;

import com.google.gson.Gson;
import io.vertx.core.Vertx;
import it.unibo.smartgh.controller.OperationPageController;
import it.unibo.smartgh.controller.OperationPageControllerImpl;
import it.unibo.smartgh.presentation.GsonUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private static final int SCENE_WIDTH = 500;
    private static final int SCENE_HEIGHT = 300;
    private static final String ID = "63af0ae025d55e9840cbc1fa";
    private final Vertx vertx = Vertx.vertx();
    private final Gson gson = GsonUtils.createGson();
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader firstPaneLoader = new FXMLLoader(ClassLoader.getSystemResource("layout/operationPage.fxml"));
        OperationPageController controller = new OperationPageControllerImpl(vertx, ID, gson);
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
