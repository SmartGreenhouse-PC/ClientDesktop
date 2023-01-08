package it.unibo.smartgh.view;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Date;

public class ParameterPageViewImpl implements ParameterPageView {
    private final String name;
    private final Double min;
    private final Double max;
    private final Double value;
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

    @FXML
    public void initialize(){
        this.parameterName.setText(name.replace(name.charAt(0), name.toUpperCase().charAt(0)));
        URL url = getClass().getClassLoader().getResource("images/"+name+".png");
        this.img.setImage(new Image(url.toExternalForm()));
        this.minValue.setText(min.toString());
        this.maxValue.setText(max.toString());
        this.currentValue.setText(value.toString());
    }

    public ParameterPageViewImpl(String name, Double min, Double max, Double currentValue) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.value = currentValue;
    }

    @Override
    public void updateCurrentValue(Double value) {
        this.currentValue.setText(value.toString());
    }
}
