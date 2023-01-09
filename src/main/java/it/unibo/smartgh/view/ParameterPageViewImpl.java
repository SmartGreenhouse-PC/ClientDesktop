package it.unibo.smartgh.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Date;

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
    private LineChart<Date, Double> lineChart;

    @FXML
    private TableView table;


    @Override
    public void setCurrentValue(String value, String status) {
        Platform.runLater(() -> {
            this.currentValue.setText(value);
            this.setColorByStatus(status);
        });
    }

    @Override
    public void initializePage(String name, String min, String max, String currentValue, String status) {
        Platform.runLater(() -> {
            this.parameterName.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
            URL url = getClass().getClassLoader().getResource("images/"+name+".png");
            this.img.setImage(new Image(url.toExternalForm()));
            this.minValue.setText(min);
            this.maxValue.setText(max);
            this.currentValue.setText(currentValue);
            this.setColorByStatus(status);
            //TODO line chart and table
        });
    }

    private void setColorByStatus(String status){
        this.currentValue.setStyle(status.equals("normal") ? "-fx-text-fill: '6DB960';" : "-fx-text-fill: 'D90000';");
    }
}
