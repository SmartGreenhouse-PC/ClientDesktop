package it.unibo.smartgh.view.operation;

import it.unibo.smartgh.controller.operation.OperationPageController;
import it.unibo.smartgh.controller.operation.OperationPageControllerImpl;
import it.unibo.smartgh.view.ApplicationView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * The implementation of the Operation page view.
 */
public class OperationPageViewImpl implements OperationPageView {

    private OperationPageController controller;

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
    private ApplicationView mainView;
    private String id;

    @Override
    public void initializeView(List<String> parameters) {
        Platform.runLater(() -> {
            this.dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            this.actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
            this.parameterColumn.setCellValueFactory(new PropertyValueFactory<>("parameter"));
            this.modalityColumn.setCellValueFactory(new PropertyValueFactory<>("modality"));

            dateColumn.setComparator(new Comparator<String>(){

                @Override
                public int compare(String t, String t1) {
                    try{
                        SimpleDateFormat format =new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                        Date d1 = format.parse(t);
                        Date d2 = format.parse(t1);
                        return Long.compare(d1.getTime(),d2.getTime());
                    }catch(ParseException p){
                        p.printStackTrace();
                    }
                    return -1;

                }

            });

            this.parameterName.getItems().add("-");
            parameters.forEach(p -> this.parameterName.getItems().add(p));
            this.parameterName.getSelectionModel().select(0);
            this.parameterName.setOnAction(this::comboBoxHandler);

            this.clearDate();
            this.disableFutureDays();
            this.dateFrom.setOnAction(this::dateFromHandler);
            this.dateTo.setOnAction(this::dateToHandler);
        });
    }

    @FXML
    public void backBtnClicked() {
        this.controller.closeSocket();
        this.mainView.changeScene("homepage.fxml");
    }

    private void dateFromHandler(ActionEvent actionEvent) {
        this.dateTo.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(dateFrom.getValue()) < 0 || date.compareTo(LocalDate.now()) > 0);
            }
        });

        this.controller.selectRange(this.dateFrom.getValue(), this.dateTo.getValue() == null ? LocalDate.now() : this.dateTo.getValue());

    }

    private void dateToHandler(ActionEvent actionEvent) {
        this.dateFrom.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(dateTo.getValue()) > 0);
            }
        });

        this.controller.selectRange(this.dateFrom.getValue() == null ? this.dateTo.getValue() : this.dateFrom.getValue(), this.dateTo.getValue());

    }

    private void disableFutureDays() {
        this.dateFrom.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });
        this.dateTo.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
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
    public void sortTable(){
        Platform.runLater(() -> {
            this.operationTable.getSortOrder().add(dateColumn);
            this.operationTable.getSortOrder().add(dateColumn);
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
        this.dateFrom.setDisable(false);
        this.dateTo.setDisable(false);
    }

    private void disableDate(){
        this.dateFrom.setDisable(true);;
        this.dateTo.setDisable(true);;
    }

    @Override
    public void initView(ApplicationView mainView, String id) {
        this.mainView = mainView;
        this.id = id;
        this.controller = new OperationPageControllerImpl(this, id);
    }

    /**
     * A class that represents a single Row in the operation table.
     */
    public class Row {
        private final String date;
        private final String modality;
        private final String parameter;
        private final String action;

        /**
         * Instantiates a new Row.
         * @param date      the date of the operation
         * @param modality  the modality of the operation
         * @param parameter the parameter of the operation
         * @param action    the action of the operation
         */
        public Row(String date, String modality, String parameter, String action) {
            this.date = date;
            this.modality = modality;
            this.parameter = parameter;
            this.action = action;
        }

        /**
         * Gets date of the operation.
         * @return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * Gets modality of the operation.
         * @return the modality
         */
        public String getModality() {
            return modality;
        }

        /**
         * Gets parameter of the operation.
         * @return the parameter
         */
        public String getParameter() {
            return parameter;
        }

        /**
         * Gets action of the operation.
         * @return the action
         */
        public String getAction() {
            return action;
        }
    }
}
