package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.controller.homepage.HomepageController;
import it.unibo.smartgh.controller.homepage.HomepageControllerImpl;
import it.unibo.smartgh.model.ParameterType;
import it.unibo.smartgh.view.ApplicationView;
import it.unibo.smartgh.view.SubView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HomepageViewImpl implements HomepageView {

    private static final String PARAMETER_LAYOUT = "layout/homepage_parameter.fxml";

    @FXML
    private ProgressIndicator loadingImg;

    @FXML
    private ImageView plantImage;

    @FXML
    private Label plantNameLabel;

    @FXML
    private Label plantDescriptionLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Button operationButton;

    @FXML
    private GridPane parameterGrid;

    private final Map<ParameterType, ParameterView> parameterViews;
    private HomepageController controller;
    private ApplicationView mainView;

    public HomepageViewImpl() {
        this.parameterViews = new HashMap<>();
    }

    @FXML
    public void initialize() {
        this.controller = new HomepageControllerImpl(this);
        List<String> parameterTypes = ParameterType.parameters();
        for (int i = 0; i < parameterTypes.size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/layout/homepage_parameter.fxml"));
                    final Parent parameter = loader.load();
                    final ParameterView view = loader.getController();
                    final ParameterType type = ParameterType.values()[i + (j * 2)];
                    view.setParameter(type);
                    this.parameterViews.put(ParameterType.values()[i + (j * 2)], view);
                    this.parameterGrid.add(parameter, i, j);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setMainView(ApplicationView mainView) {
        this.mainView = mainView;
        this.parameterViews.values().forEach(p -> p.setMainView(mainView));
    }

    @FXML
    public void operationBtnClicked() {
        this.mainView.changeScene("homepage_parameter.fxml"); //todo
    }
}
