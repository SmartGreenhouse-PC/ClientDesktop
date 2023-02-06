package it.unibo.smartgh.view.parameter;

import it.unibo.smartgh.controller.parameter.ParameterPageController;
import it.unibo.smartgh.controller.parameter.ParameterPageControllerImpl;
import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.view.ApplicationView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Map;

/**
 * The implementation of the view of Parameter page.
 */
public class ParameterPageViewImpl implements ParameterPageView {

    @FXML
    private Label parameterName;

    @FXML
    private Label minValue;

    @FXML
    private Label currentValue;

    @FXML
    private Label maxValue;

    @FXML
    private ImageView img;

    @FXML
    private CategoryAxis xAxis ;

    @FXML
    private NumberAxis yAxis ;

    @FXML
    private AreaChart<CategoryAxis, NumberAxis> areaChart;

    @FXML
    private TableView table;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn valueColumn;

    private ApplicationView mainView;
    private ParameterPageController controller;

    @Override
    public void updateValues(String value, String status, Map<String, Double> history) {
        Platform.runLater(() -> {
            this.currentValue.setText(value);
            this.setColorByStatus(status);
            this.setHistory(history, status);
        });
    }

    @Override
    public void initView(ApplicationView mainView, String id) {
        this.mainView = mainView;
        this.controller = new ParameterPageControllerImpl(this, id);
    }

    @Override
    public void setParameter(ParameterType parameter) {
        this.controller.setParameter(parameter);
    }

    @Override
    public void initializePage(ParameterType parameter, String min, String max, String currentValue,
                               Map<String, Double> history,
                               String status) {
        Platform.runLater(() -> {
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

            this.parameterName.setText(parameter.getTitle());
            this.img.setImage(new Image(parameter.getImagePath()));
            this.minValue.setText(min);
            this.maxValue.setText(max);
            this.currentValue.setText(currentValue);
            this.setColorByStatus(status);
            this.setHistory(history, status);
        });
    }

    /**
     * Handler of the back button.
     */
    @FXML
    public void backButtonClicked() {
        this.controller.closeSocket();
        this.mainView.changeScene("homepage.fxml");
    }

    private void setHistory(Map<String, Double> history, String status) {
        areaChart.getData().clear();
        table.getItems().clear();

        yAxis.setLowerBound(0);
        yAxis.setLowerBound(20_000);
        areaChart.getXAxis().setLabel("date and time");
        areaChart.getYAxis().setLabel("values");

        XYChart.Series series = new XYChart.Series<>();
        series.setName("rilevazioni");
        history.forEach((k,v) -> {
            series.getData().add(new XYChart.Data<>(k, v));
            table.getItems().add(new Row(k, v));
        });
        areaChart.getData().add(series);
        areaChart.getStyleClass().removeIf(s -> s.contains("StateChart"));
        areaChart.getStyleClass().add(status + "StateChart");
    }

    private void setColorByStatus(String status){
        this.currentValue.getStyleClass().removeIf(c -> c.contains("State"));
        this.currentValue.getStyleClass().add(status + "State");
    }

    /**
     * A class that represent a single parameter value row.
     */
    public class Row {
        private final String date;
        private final Double value;

        /**
         * Instantiates a new parameter Row.
         * @param date  the date of the parameter
         * @param value the value of the parameter
         */
        public Row(String date, Double value) {
            this.date = date;
            this.value = value;
        }

        /**
         * Gets the date of the row.
         * @return the parameter date
         */
        public String getDate() {
            return date;
        }

        /**
         * Gets value of the row.
         * @return the parameter value
         */
        public Double getValue() {
            return value;
        }
    }
}
