package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.controller.homepage.HomepageController;
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

/**
 * The implementation of {@link HomepageParameterView} interface.
 */
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
    private HomepageController controller;
    private ParameterType parameter;
    private String unit;
    private String parameterStatus = "";

    /**
     * Handler called when a parameter is clicked.
     */
    @FXML
    public void parameterClicked() {
        this.controller.closeSocket();
        Optional<SubView> parameterPage = this.mainView.changeScene("parameterPage.fxml");
        if (parameterPage.isPresent() && parameterPage.get() instanceof ParameterPageView) {
            ((ParameterPageView) parameterPage.get()).setParameter(this.parameter);
        }
    }

    @Override
    public void setParameter(ParameterType parameter) {
        Platform.runLater(() -> {
            this.parameter = parameter;
            this.parameterName.setText(parameter.getTitle());
            this.parameterImg.setImage(new Image(parameter.getImagePath()));
        });
    }

    @Override
    public void setOptimalValue(Double minValue, Double maxValue, String unit) {
        this.unit = unit;
        Platform.runLater(() -> this.optimalValueLabel.setText(minValue + " " + unit + " - " + maxValue + " " + unit));
    }

    @Override
    public void setCurrentValue(Double value, String status) {
        Platform.runLater(() -> {
            if(value != null) {
                this.currentValueLabel.setText(value + " " + unit);

            } else {
            this.currentValueLabel.setText(" " + unit);
            }
        if (!this.parameterStatus.equals(status)) {
            this.currentValueLabel.getStyleClass().removeAll(this.parameterStatus + "State");
            this.parameterStatus = status;
            this.currentValueLabel.getStyleClass().add(this.parameterStatus + "State");

        }});
    }

    @Override
    public String getParameterStatus() {
        return parameterStatus;
    }

    @Override
    public void setController(HomepageController controller) {
        this.controller = controller;
    }

    @Override
    public void initView(ApplicationView mainView, String id) {
        this.mainView = mainView;
    }
}
