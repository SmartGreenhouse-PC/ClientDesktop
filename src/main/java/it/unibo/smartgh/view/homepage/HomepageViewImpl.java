package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.controller.homepage.HomepageController;
import it.unibo.smartgh.controller.homepage.HomepageControllerImpl;
import it.unibo.smartgh.model.ParameterType;
import it.unibo.smartgh.view.ApplicationView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    private ApplicationView mainView;
    private String status;

    public HomepageViewImpl() {
        this.parameterViews = new HashMap<>();
        this.status = "";
    }

    @FXML
    public void initialize() {
        final HomepageController controller = new HomepageControllerImpl(this, "63af0ae025d55e9840cbc1fa"); //todo id
        final List<String> parameterTypes = ParameterType.parameters();
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

    @FXML
    public void operationBtnClicked() {
        this.mainView.changeScene("homepage_parameter.fxml"); //todo
    }

    @Override
    public void setMainView(ApplicationView mainView) {
        this.mainView = mainView;
        this.parameterViews.values().forEach(p -> p.setMainView(mainView));
    }

    @Override
    public void setPlantInformation(String name, String description, String img) {
        Platform.runLater(() -> {
            this.plantNameLabel.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
            this.plantDescriptionLabel.setText(description.substring(0, 1).toUpperCase() + description.substring(1));
            this.plantImage.setImage(new Image(img));
            this.loadingImg.setVisible(false);
        });
    }

    @Override
    public void setParameterInfo(ParameterType parameterType, Double minValue, Double maxValue, String unit) {
        this.parameterViews.get(parameterType).setOptimalValue(minValue, maxValue, unit);
    }

    @Override
    public void updateParameterValue(ParameterType parameterType, Double value, String status) {
        this.parameterViews.get(parameterType).setCurrentValue(value, status);
        Platform.runLater(() -> {
            if (!this.status.equals(this.statusLabel.getText())) {
                Optional<String> isAlarm = this.parameterViews.values().stream().map(ParameterView::getParameterStatus).filter(s -> s.equals("alarm")).findFirst();
                this.statusLabel.getStyleClass().removeAll(this.status + "State");
                if (isAlarm.isPresent()) {
                    this.statusLabel.setText("ALLARME");
                    this.status = "alarm";
                } else {
                    this.statusLabel.setText("NORMALE");
                    this.status = "normal";
                }
                this.statusLabel.getStyleClass().add(this.status + "State");
            }
        });
    }
}
