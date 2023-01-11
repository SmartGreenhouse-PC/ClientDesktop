package it.unibo.smartgh.view;

import it.unibo.smartgh.controller.OperationPageController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class OperationPageViewImpl implements OperationPageView {
    private final OperationPageController controller;
    @FXML
    private TableView operationTable;

    @FXML
    private TableColumn dateColumn;

    @FXML
    private TableColumn actionColumn;

    @FXML
    private TableColumn parameterColumn;

    @FXML
    private TableColumn modalityColumn;

    @FXML
    private ComboBox<String> parameterName;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    public OperationPageViewImpl(OperationPageController controller) {
        this.controller = controller;
    }

    @Override
    public void initializeView(List<String> parameters) {
        Platform.runLater(() -> {
            this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            this.actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
            this.parameterColumn.setCellValueFactory(new PropertyValueFactory<>("parameter"));
            this.modalityColumn.setCellValueFactory(new PropertyValueFactory<>("modality"));

            this.parameterName.getItems().add("-");
            parameters.forEach(p -> this.parameterName.getItems().add(p));
            this.parameterName.getSelectionModel().select(0);
            this.parameterName.setOnAction(this::comboBoxHandler);

            this.clearDate();
        });
    }


    private void comboBoxHandler(ActionEvent event) {
        String value = this.parameterName.getValue();
        clearDate();
        if (value.equals("-")) {
            enableDate();
        } else {
            disableDate();
        }
        this.controller.changeSelectedParameter(value);
    }


    @Override
    public void addTableRow(String date, String modality, String parameter, String action) {
        Platform.runLater(() -> {
            this.operationTable.getItems().add(new Row(date, modality, parameter, action));
        });
    }

    @Override
    public void clearTable() {
        Platform.runLater(() -> {
            this.operationTable.getItems().clear();
        });
    }

    private void clearDate(){
        this.dateFrom.setValue(null);
        this.dateTo.setValue(null);
    }

    private void enableDate(){
        this.dateFrom.setDisable(true);
        this.dateTo.setDisable(true);
    }

    private void disableDate(){
        this.dateFrom.setDisable(false);
        this.dateTo.setDisable(false);
    }

    public class Row {
        private final String date;
        private final String modality;
        private final String parameter;
        private final String action;

        public Row(String date, String modality, String parameter, String action) {
            this.date = date;
            this.modality = modality;
            this.parameter = parameter;
            this.action = action;
        }

        public String getDate() {
            return date;
        }

        public String getModality() {
            return modality;
        }

        public String getParameter() {
            return parameter;
        }

        public String getAction() {
            return action;
        }
    }
}
