package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.view.ApplicationView;
import it.unibo.smartgh.view.SubView;
import it.unibo.smartgh.view.parameter.ParameterPageView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class HomepageParameterViewImpl implements HomepageParameterView {

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
    private String id;

    @FXML
    public void parameterClicked() {
        Optional<SubView> parameterPage = this.mainView.changeScene("parameterPage.fxml");
        if (parameterPage.isPresent() && parameterPage.get() instanceof ParameterPageView) {
            ((ParameterPageView) parameterPage.get()).setParameter(this.parameter.getName());
        }
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
    public void initView(ApplicationView mainView, String id) {
        this.mainView = mainView;
        this.id = id;
    }
}
