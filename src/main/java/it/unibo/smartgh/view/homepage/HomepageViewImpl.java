package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.controller.homepage.HomepageControllerImpl;
import it.unibo.smartgh.model.parameter.ParameterType;
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

/**
 * The implementation of {@link HomepageView} interface.
 */
public class HomepageViewImpl implements HomepageView {

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

    private final Map<ParameterType, HomepageParameterView> parameterViews;
    private ApplicationView mainView;
    private HomepageControllerImpl controller;
    private String status;
    private String id;

    /**
     * Instantiates a new Homepage view.
     */
    public HomepageViewImpl() {
        this.parameterViews = new HashMap<>();
        this.status = "";
    }

    @FXML
    public void initialize() {
        final List<String> parameterTypes = ParameterType.parameters();
        for (int i = 0; i < parameterTypes.size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/layout/homepage_parameter.fxml"));
                    final Parent parameter = loader.load();
                    final HomepageParameterView view = loader.getController();
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
        this.mainView.changeScene("operationPage.fxml");
    }

    @Override
    public void initView(ApplicationView mainView, String id) {
        this.mainView = mainView;
        this.id = id;
        this.parameterViews.values().forEach(p -> p.initView(mainView, this.id));
        this.controller = new HomepageControllerImpl(this, this.id);
        this.controller.initializeData();
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
                Optional<String> isAlarm = this.parameterViews.values().stream().map(HomepageParameterView::getParameterStatus).filter(s -> s.equals("alarm")).findFirst();
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
