package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.model.ParameterType;
import it.unibo.smartgh.view.ApplicationView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ParameterViewImpl implements ParameterView {

    @FXML
    private Label parameterName;

    @FXML
    private Label optimalValueLabel;

    @FXML
    private ImageView parameterImg;

    @FXML
    private Label currentValueLabel;

    private ApplicationView mainView;
    private ParameterType parameter;
    private String unit;

    private String parameterStatus = "";

    public ParameterViewImpl() {

    }

    @FXML
    public void initialize() {
        //todo
    }

    @FXML
    public void parameterClicked() {
        this.mainView.changeScene("parameterPage.fxml");
    }

    @Override
    public void setParameter(ParameterType parameter) {
        Platform.runLater(() -> {
            this.parameter = parameter;
            this.parameterName.setText(parameter.getTitle());
            this.parameterImg.setImage(new Image("/images/" + parameter.getImagePath()));
        });
    }

    @Override
    public void setOptimalValue(Double minValue, Double maxValue, String unit) {
        this.unit = unit;
        Platform.runLater(() -> this.optimalValueLabel.setText(minValue + " " + unit + " - " + maxValue + " " + unit));
    }

    @Override
    public void setCurrentValue(Double value, String status) {
        Platform.runLater(() -> this.currentValueLabel.setText(value + " " + unit));
        if (!this.parameterStatus.equals(status)) {
            this.currentValueLabel.getStyleClass().removeAll(this.parameterStatus + "State");
            this.parameterStatus = status;
            this.currentValueLabel.getStyleClass().add(this.parameterStatus + "State");
        }
    }

    @Override
    public String getParameterStatus() {
        return parameterStatus;
    }

    @Override
    public void setMainView(ApplicationView mainView) {
        this.mainView = mainView;
    }
}
