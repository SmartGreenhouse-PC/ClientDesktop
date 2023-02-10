package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.view.AbstractViewTest;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

/**
 * Test to verify the correct behaviour of application homepage.
 */
@ExtendWith(ApplicationExtension.class)
class HomepageViewImplTest extends AbstractViewTest {

    private static final String HOMEPAGE_LAYOUT = "homepage.fxml";
    protected HomepageView view;

    @Start
    public void start(final Stage stage) {
        try {
            this.view = (HomepageView) setupScene(stage, HOMEPAGE_LAYOUT);
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

    @Test
    public void testSelectGreenhouse(FxRobot robot){
        final ComboBox greenhouses = robot.lookup("#greenhouses").queryAs(ComboBox.class);
        greenhouses.getItems().add("greenhouse1");
        greenhouses.getItems().add("greenhouse2");
        greenhouses.getSelectionModel().select(0);
        await().pollInterval(Duration.TWO_HUNDRED_MILLISECONDS).atMost(Duration.FIVE_SECONDS).untilAsserted(() -> {
           String value = greenhouses.getValue().toString();
            assertEquals("greenhouse1", value);
        });


    }
}