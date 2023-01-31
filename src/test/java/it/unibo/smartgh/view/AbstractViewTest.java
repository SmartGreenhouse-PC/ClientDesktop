package it.unibo.smartgh.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;

public abstract class AbstractViewTest {

    protected Parent scene;

    @BeforeAll
    static public void setup() {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("java.awt.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        WaitForAsyncUtils.checkAllExceptions = false;
        WaitForAsyncUtils.autoCheckException = false;
    }

    public SubView setupScene(final Stage stage, String layout) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/" + layout));
        this.scene = loader.load();
        SubView view = loader.getController();
        stage.setScene(new Scene(scene));
        stage.show();

        stage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        return view;
    }
}
