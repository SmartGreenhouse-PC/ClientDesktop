package it.unibo.smartgh.view.homepage;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.awaitility.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class HomepageViewImplTest {

    private static final String HOMEPAGE_LAYOUT = "/layout/homepage.fxml";
    private HomepageView view;
    private Parent scene;

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

    @Start
    public void start(final Stage stage) {
        try {
            final FXMLLoader loader = new FXMLLoader(getClass().getResource(HOMEPAGE_LAYOUT));
            this.scene = loader.load();
            this.view = loader.getController();
            stage.setScene(new Scene(scene));
            stage.show();

            stage.setOnCloseRequest(t -> {
                Platform.exit();
                System.exit(0);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPlantInformation(FxRobot robot) {
        final Label plantName = robot.lookup("#plantNameLabel").queryAs(Label.class);
        final Label plantDescription = robot.lookup("#plantDescriptionLabel").queryAs(Label.class);
        final ImageView plantImage = robot.lookup("#plantImage").queryAs(ImageView.class);
        this.view.setPlantInformation("Plant name", "Plant description", "http://is.am/5b4x");

        await().pollInterval(Duration.TWO_HUNDRED_MILLISECONDS).atMost(Duration.FIVE_SECONDS).untilAsserted(() -> {
            assertThat(plantName).hasText("Plant name");
            assertThat(plantDescription).hasText("Plant description");
            assertNotNull(plantImage.getImage());
            assertFalse(plantImage.getImage().isError());
        });
    }
}