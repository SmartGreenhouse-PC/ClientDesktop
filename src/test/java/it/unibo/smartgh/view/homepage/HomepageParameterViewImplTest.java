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
class HomepageParameterViewImplTest extends AbstractViewTest {

    private static final String HOMEPAGE_PARAMETER_LAYOUT = "homepage_parameter.fxml";
    private final ParameterType parameterType = ParameterType.TEMPERATURE;
    private final PlantParameter parameter = new PlantParameterBuilder(parameterType.getName()).min(8.0).max(35.0).unit("\u2103").build();
    private final Double currentValue = 7.0;
    private HomepageParameterView view;

    @Start
    public void start(final Stage stage) {
        try {
            this.view = (HomepageParameterView) setupScene(stage, HOMEPAGE_PARAMETER_LAYOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParameter(FxRobot robot) {
        final Label parameterName = robot.lookup("#parameterName").queryAs(Label.class);
        final Label optimalValueLabel = robot.lookup("#optimalValueLabel").queryAs(Label.class);
        final ImageView parameterImg = robot.lookup("#parameterImg").queryAs(ImageView.class);
        final Label currentValueLabel = robot.lookup("#currentValueLabel").queryAs(Label.class);
        this.view.setParameter(parameterType);
        this.view.setOptimalValue(parameter.getMin(), parameter.getMax(), parameter.getUnit());
        this.view.setCurrentValue(currentValue, currentValue > parameter.getMin() && currentValue < parameter.getMax() ? "normal" : "alarm");

        await().pollInterval(Duration.TWO_HUNDRED_MILLISECONDS).atMost(Duration.FIVE_SECONDS).untilAsserted(() -> {
            assertThat(parameterName).hasText(parameterType.getTitle());
            assertThat(optimalValueLabel).hasText(parameter.getMin() + " " + parameter.getUnit() + " - " + parameter.getMax() + " " + parameter.getUnit());
            assertNotNull(parameterImg.getImage());
            assertFalse(parameterImg.getImage().isError());
            assertThat(currentValueLabel).hasText(currentValue + " " + parameter.getUnit());
        });
    }

}