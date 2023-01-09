package it.unibo.smartgh.view;

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




    @Override
    public void updateValues(String value, String status, Map<String, Double> history) {
        Platform.runLater(() -> {
            this.currentValue.setText(value);
            this.setColorByStatus(status);
            this.setHistory(history, status.equals("normal") ? "6DB960" : "D90000");
        });
    }

    @Override
    public void initializePage(String name, String min, String max, String currentValue, Map<String, Double> history, String status) {
        Platform.runLater(() -> {
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

            this.parameterName.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
            URL url = getClass().getClassLoader().getResource("images/"+name+".png");
            this.img.setImage(new Image(url.toExternalForm()));
            this.minValue.setText(min);
            this.maxValue.setText(max);
            this.currentValue.setText(currentValue);
            this.setColorByStatus(status);
            this.setHistory(history, status.equals("normal") ? "6DB960" : "D90000");

        });
    }

    private void setHistory(Map<String, Double> history, String color) {
        areaChart.getData().clear();
        table.getItems().clear();

        yAxis.setLowerBound(0);
        yAxis.setLowerBound(20_000);
        areaChart.getXAxis().setLabel("date and time");
        areaChart.getYAxis().setLabel("values");

        XYChart.Series series = new XYChart.Series();
        series.setName("parameter history");
        history.forEach((k,v) -> {
            series.getData().add(new XYChart.Data(k, v));
            table.getItems().add(new Row(k, v));
        });
        areaChart.getData().add(series);
        Node fill = series.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
        Node line = series.getNode().lookup(".chart-series-area-line");

        line.setStyle("-fx-stroke: #" + color);
        fill.setStyle("-fx-fill: #" + color);

    }

    private void setColorByStatus(String status){
        this.currentValue.setStyle(status.equals("normal") ? "-fx-text-fill: '6DB960';" : "-fx-text-fill: 'D90000';");
    }

    public class Row {
        private final String date;
        private final Double value;
        public Row(String date, Double value) {
            this.date = date;
            this.value = value;
        }

        public String getDate() {
            return date;
        }

        public Double getValue() {
            return value;
        }
    }
}
