package it.unibo.smartgh.view.homepage;

import it.unibo.smartgh.controller.homepage.HomepageController;
import it.unibo.smartgh.controller.homepage.HomepageControllerImpl;
import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.view.ApplicationView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
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
    private GridPane parameterGrid;

    @FXML
    private ComboBox<String> greenhouses;

    private final Map<ParameterType, HomepageParameterView> parameterViews;
    private ApplicationView mainView;
    private HomepageController controller;
    private String status;

    /**
     * Instantiates a new Homepage view.
     */
    public HomepageViewImpl() {
        this.parameterViews = new HashMap<>();
        this.status = "";
    }

    /**
     * Initialize the view.
     */
    @FXML
    public void initialize() {
        for (int i = 0; i < ParameterType.values().length / 2; i++) {
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

    /**
     * Handler called when operation button is clicked.
     */
    @FXML
    public void operationBtnClicked() {
        this.controller.closeSocket();
        this.mainView.changeScene("operationPage.fxml");
    }

    @Override
    public void initView(ApplicationView mainView, String id) {
        this.mainView = mainView;
        this.controller = new HomepageControllerImpl(this);
        this.parameterViews.values().forEach(p -> {
            p.initView(mainView, id);
            p.setController(controller);
        });
        controller.initializeData();
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

    @Override
    public void initializeGreenhousesSelector(List<String> greenhousesId) {
        Platform.runLater(() -> {
            greenhousesId.forEach(id -> this.greenhouses.getItems().add(id));
            this.greenhouses.setOnAction(this::selectGreenhouseId);
            this.greenhouses.getSelectionModel().select(0);
        });


    }

    private void selectGreenhouseId(ActionEvent actionEvent) {
        String id = this.greenhouses.getValue();
        this.controller.greenhouseSelected(id);
        this.mainView.setId(id);
    }
}
