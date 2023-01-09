package it.unibo.smartgh.view.homepage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ParameterViewImpl implements ParameterView {

    @FXML
    private Label parameterName;

    @FXML
    private ImageView parameterImg;

    private final String parameter;

    public ParameterViewImpl(String parameter) {
        this.parameter = parameter;
    }

    @FXML
    public void initialize() {
        this.parameterName.setText(parameter);
//      todo this.parameterImg.setImage(new Image(parameter + ".png"));
    }
}
