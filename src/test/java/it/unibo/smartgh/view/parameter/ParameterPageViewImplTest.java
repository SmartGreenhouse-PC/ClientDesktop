package it.unibo.smartgh.view.parameter;

import it.unibo.smartgh.model.parameter.*;
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
import java.util.*;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ParameterPageViewImplTest extends AbstractViewTest {

    private static final String PARAMETER_LAYOUT = "parameterPage.fxml";
    private static final String ID = "1";
    private final Date firstDate = new Date(1_220_500_200L * 1_000L);
    private final Date secondDate = new Date(1_220_100_200L * 1_000L);
    private final ParameterType type = ParameterType.TEMPERATURE;
    private final PlantParameter plantParameter = new PlantParameterBuilder(type.getName()).min(8.0).max(35.0).unit("\u2103").build();
    private final ParameterValue currentValue = new ParameterValueImpl(ID, firstDate, 7.0);
    private final List<ParameterValue> history = Arrays.asList(currentValue, new ParameterValueImpl(ID, secondDate, 15.0));
    private final Parameter parameter = new ParameterImpl(type.getName(), currentValue, history);
    private ParameterPageView view;

    @Start
    public void start(final Stage stage) {
        try {
            this.view = (ParameterPageView) setupScene(stage, PARAMETER_LAYOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParameterValue(FxRobot robot) {
        final Label parameterName = robot.lookup("#parameterName").queryAs(Label.class);
        final Label minValue = robot.lookup("#minValue").queryAs(Label.class);
        final Label maxValue = robot.lookup("#maxValue").queryAs(Label.class);
        final ImageView parameterImg = robot.lookup("#img").queryAs(ImageView.class);
        final Label currentValueLabel = robot.lookup("#currentValue").queryAs(Label.class);
        this.view.initializePage(type, plantParameter.getMin().toString(), plantParameter.getMax().toString(),
                this.parameter.getCurrentValue().getValue().toString() +  plantParameter.getUnit(),
                this.parameter.getHistoryAsMap(), "alarm");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        await().pollInterval(Duration.TWO_HUNDRED_MILLISECONDS).atMost(Duration.FIVE_SECONDS).untilAsserted(() -> {
            assertThat(parameterName).hasText(type.getTitle());
            assertThat(minValue).hasText(plantParameter.getMin().toString());
            assertThat(maxValue).hasText(plantParameter.getMax().toString());
            assertNotNull(parameterImg.getImage());
            assertFalse(parameterImg.getImage().isError());
            assertThat(currentValueLabel).hasText(currentValue.getValue() + plantParameter.getUnit());
        });
    }
}