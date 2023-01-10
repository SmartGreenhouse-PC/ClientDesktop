package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.model.ParameterType;
import it.unibo.smartgh.view.ApplicationView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ParameterViewImpl implements ParameterView {

    @FXML
    private Label parameterName;

    @FXML
    private ImageView parameterImg;

    private ApplicationView mainView;
    private ParameterType parameter;

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
        this.parameter = parameter;
        this.parameterName.setText(parameter.getName());
//        this.parameterImg.setImage(new Image("/images/" + parameter.getImagePath()));
    }

    @Override
    public void setMainView(ApplicationView mainView) {
        this.mainView = mainView;
    }
}
