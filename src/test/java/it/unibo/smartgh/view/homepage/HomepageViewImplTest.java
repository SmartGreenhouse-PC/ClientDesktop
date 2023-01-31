package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.model.plant.PlantParameter;
import it.unibo.smartgh.model.plant.PlantParameterBuilder;
import it.unibo.smartgh.view.AbstractViewTest;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class HomepageViewImplTest extends AbstractViewTest {

    private static final String HOMEPAGE_LAYOUT = "homepage.fxml";
    private static final String HOMEPAGE_PARAMETER_LAYOUT = "homepage_parameter.fxml";
    private final ParameterType parameterType = ParameterType.TEMPERATURE;
    private final PlantParameter parameter = new PlantParameterBuilder(parameterType.getName()).min(8.0).max(35.0).unit("\u2103").build();
    private final Double currentValue = 7.0;
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
}