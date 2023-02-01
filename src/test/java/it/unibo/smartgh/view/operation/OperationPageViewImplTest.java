package it.unibo.smartgh.view.operation;

import it.unibo.smartgh.model.greenhouse.Modality;
import it.unibo.smartgh.model.operation.Operation;
import it.unibo.smartgh.model.operation.OperationImpl;
import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.view.AbstractViewTest;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to verify the correct behaviour in the application operation page.
 */
@ExtendWith(ApplicationExtension.class)
class OperationPageViewImplTest extends AbstractViewTest {

    private static final String OPERATION_LAYOUT = "operationPage.fxml";
    private static final String ID = "1";
    private static final String ACTION = "on";
    private final Operation operation = new OperationImpl(ID, Modality.MANUAL, new Date(), ParameterType.TEMPERATURE.getName(), ACTION);
    private OperationPageView view;


    @Start
    public void start(final Stage stage) {
        try {
            this.view = (OperationPageView) setupScene(stage, OPERATION_LAYOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOperationHistory(FxRobot robot) {
        final TableView<OperationPageViewImpl.Row> table = robot.lookup("#operationTable").queryTableView();
        final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        this.view.initializeView(Arrays.stream(ParameterType.values()).map(ParameterType::getName).toList());
        this.view.addTableRow(formatter.format(operation.getDate()), operation.getModality().toString(), operation.getParameter(), operation.getAction());

        await().pollInterval(Duration.TWO_HUNDRED_MILLISECONDS).atMost(Duration.FIVE_SECONDS).untilAsserted(() ->
                table.getItems().forEach(row -> {
                    assertEquals(formatter.format(operation.getDate()), row.getDate());
                    assertEquals(operation.getParameter(), row.getParameter());
                    assertEquals(operation.getAction(), row.getAction());
                    assertEquals(operation.getModality().name(), row.getModality());
                }));
    }
}