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
    public void setCurrentValue(Double value) {
        Platform.runLater(() -> {
            this.currentValue.setText(value.toString());
        });
    }


    @Override
    public void initializePage(String name, Double min, Double max, Double currentValue) {
        Platform.runLater(() -> {
            this.parameterName.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
            URL url = getClass().getClassLoader().getResource("images/"+name+".png");
            this.img.setImage(new Image(url.toExternalForm()));
            this.minValue.setText(min.toString());
            this.maxValue.setText(max.toString());
            this.currentValue.setText(currentValue.toString());
            //TODO line chart and table
        });

    }
}
