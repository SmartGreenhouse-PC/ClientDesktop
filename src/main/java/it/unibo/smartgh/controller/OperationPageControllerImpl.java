package it.unibo.smartgh.controller;

import com.google.gson.Gson;
import io.vertx.core.json.JsonArray;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import it.unibo.smartgh.model.*;
import it.unibo.smartgh.view.OperationPageView;
import it.unibo.smartgh.view.OperationPageViewImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class OperationPageControllerImpl implements OperationPageController {

    private final static int PORT = 8890;
    private final static int SOCKET_PORT = 1234;
    private final static String SOCKET_HOST = "localhost";
    private final static String HOST = "localhost";
    private final static String GREENHOUSE_PATH = "/clientCommunication/greenhouse";
    private final static String BASE_PATH = "/clientCommunication/operations";
    private final static String PARAM_PATH = BASE_PATH + "/parameter";
    private final static String DATE_PATH = BASE_PATH + "/date";
    private final static int LIMIT = 20;
    private final Vertx vertx;
    private final String id;
    private final Gson gson;

    private OperationPageView view;
    private final WebClient client;
    private String selectedParameterFilter;

    public OperationPageControllerImpl(Vertx vertx, String id, Gson gson) {
        this.vertx = vertx;
        this.id = id;
        this.gson = gson;
        this.view = new OperationPageViewImpl(this);
        this.client = WebClient.create(vertx);
        this.initializeView();
        this.setSocket();
    }

    @Override
    public OperationPageView getView() {
        return this.view;
    }

    @Override
    public void changeSelectedParameter(String parameter) {
        this.selectedParameterFilter = parameter;
        if (!parameter.equals("-")) {
            client.get(PORT, HOST, PARAM_PATH)
                    .addQueryParam("id", id)
                    .addQueryParam("limit", String.valueOf(LIMIT))
                    .addQueryParam("parameterName", parameter)
                    .send()
                    .onSuccess(resp -> {
                        this.view.clearTable();
                        JsonArray array = resp.body().toJsonArray();
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                        array.forEach(o -> {
                            Operation op = gson.fromJson(o.toString(), OperationImpl.class);
                            this.view.addTableRow(formatter.format(op.getDate()),
                                    op.getModality().toString(),
                                    op.getParameter(),
                                    op.getAction());
                        });
                    })
                    .onFailure(System.out::println);
        } else {
            this.populateTableWithAllOperations();
        }
    }

    @Override
    public void selectRange(LocalDate from, LocalDate to) {
        System.out.println(to);
        client.get(PORT, HOST, DATE_PATH)
                .addQueryParam("id", id)
                .addQueryParam("limit", String.valueOf(LIMIT))
                .addQueryParam("from", "2023-01-11")
                .addQueryParam("to", "2023-01-11")
                .send()
                .onSuccess(resp -> {
                    this.view.clearTable();
                    System.out.println(resp.body().toString());
                    JsonArray array = resp.body().toJsonArray();
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                    array.forEach(o -> {
                        Operation op = gson.fromJson(o.toString(), OperationImpl.class);
                        this.view.addTableRow(formatter.format(op.getDate()),
                                op.getModality().toString(),
                                op.getParameter(),
                                op.getAction());
                    });
                })
                .onFailure(System.out::println);
    }

    private void setSocket() {
        //TODO
    }

    private void initializeView() {
        this.client.get(PORT, HOST, GREENHOUSE_PATH)
                .addQueryParam("id", id)
                .as(BodyCodec.string())
                .send()
                .onSuccess(resp -> {
                    Greenhouse greenhouse = gson.fromJson(resp.body(), GreenhouseImpl.class);
                    greenhouse.setId(this.id);
                    this.view.initializeView(greenhouse.getPlant().getUnitMap().keySet().stream().collect(Collectors.toList()));
                })
                .onFailure(System.out::println)
                .andThen(r -> {
                    populateTableWithAllOperations();
                });

    }

    private void populateTableWithAllOperations() {
        client.get(PORT, HOST, BASE_PATH)
                .addQueryParam("id", id)
                .addQueryParam("limit", String.valueOf(LIMIT))
                .send()
                .onSuccess(resp -> {
                    System.out.println(resp.body().toJsonArray());
                    JsonArray array = resp.body().toJsonArray();
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                    array.forEach(o -> {
                        Operation op = gson.fromJson(o.toString(), OperationImpl.class);
                        this.view.addTableRow(formatter.format(op.getDate()),
                                op.getModality().toString(),
                                op.getParameter(),
                                op.getAction());
                    });
                })
                .onFailure(System.out::println);
    }
}
