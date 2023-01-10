package it.unibo.smartgh.controller;

import com.google.gson.Gson;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import it.unibo.smartgh.model.*;
import it.unibo.smartgh.view.ParameterPageView;
import it.unibo.smartgh.view.ParameterPageViewImpl;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ParameterPageControllerImpl implements ParameterPageController {
    private final static int PORT = 8890;
    private final static int SOCKET_PORT = 1234;
    private final static String SOCKET_HOST = "localhost";
    private final static String HOST = "localhost";
    private final static String BASE_PATH = "/clientCommunication";
    private static final String GREENHOUSE_PATH = BASE_PATH + "/greenhouse";
    private static final String PARAMETER_PATH = BASE_PATH + "/parameter";
    private static final String PARAMETER_HISTORY_PATH = PARAMETER_PATH + "/history";

    private final String id;
    private final String parameterName;
    private final Vertx vertx;
    private final Gson gson;
    private Parameter parameter;
    private Double min;
    private Double max;

    private String unit;

    ParameterPageView view;

    public ParameterPageControllerImpl(Vertx vertx, String id, Gson gson, String parameter) {
        this.gson = gson;
        this.id = id;
        this.parameterName = parameter;
        this.vertx = vertx;
        this.view = new ParameterPageViewImpl();
        this.updateView();
        this.setSocket();
    }

    private void setSocket() {
        HttpServer server = vertx.createHttpServer();
        server.webSocketHandler(ctx -> {
            ctx.textMessageHandler(msg -> {
                JsonObject json = new JsonObject(msg);
                if(json.getValue("greenhouseId").equals(this.id)) {
                    if (json.getValue("parameterName").equals(parameterName)) {
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                        this.parameter.getCurrentValue().setValue(Double.valueOf(json.getValue("value").toString()));
                        try {
                            this.parameter.getCurrentValue().setDate(formatter.parse(json.getString("date")));
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        var newHistory = this.parameter.getHistory();
                        newHistory.remove(0);
                        ParameterValue newParamValue = new ParameterValueImpl(this.id, this.parameter.getCurrentValue().getDate(), this.parameter.getCurrentValue().getValue());
                        newHistory.add(newParamValue);
                        this.parameter.setHistory(newHistory);
                        this.view.updateValues(json.getValue("value").toString() + unit, this.status(), this.parameter.getHistoryAsMap());
                    }
                }
            });
        }).listen(SOCKET_PORT, SOCKET_HOST);

    }

    @Override
    public ParameterPageView getView() {
        return this.view;
    }

    private String status(){
        return this.parameter.getCurrentValue().getValue().compareTo(this.min) >=0 &&
                this.parameter.getCurrentValue().getValue().compareTo(this.max) <=0 ? "normal" : "alarm";
    }

    private void updateView(){
        WebClient client = WebClient.create(vertx);
        client.get(PORT, HOST, GREENHOUSE_PATH)
                .addQueryParam("id", id)
                .as(BodyCodec.string())
                .send()
                .onSuccess(resp -> {
                    Greenhouse greenhouse = gson.fromJson(resp.body(), GreenhouseImpl.class);
                    greenhouse.setId(this.id);
                    this.unit = greenhouse.getPlant().getUnitMap().get(this.parameterName);
                    this.min = this.paramOptimalValue("Min", this.parameterName, greenhouse.getPlant());
                    this.max = this.paramOptimalValue("Max", this.parameterName, greenhouse.getPlant());
                })
                .onFailure(System.out::println)
                .andThen( resp -> {
                    client.get(PORT, HOST, PARAMETER_PATH)
                            .addQueryParam("id", id)
                            .addQueryParam("parameterName", this.parameterName)
                            .as(BodyCodec.string())
                            .send()
                            .onSuccess(r -> {
                                ParameterValue val = gson.fromJson(r.body(), ParameterValueImpl.class);
                                this.parameter = new ParameterImpl(this.parameterName, val);
                            })
                            .onFailure(System.out::println)
                            .andThen(resp2 -> {
                                client.get(PORT, HOST,PARAMETER_HISTORY_PATH)
                                        .addQueryParam("limit","10")
                                        .addQueryParam("parameterName", this.parameterName)
                                        .addQueryParam("id", this.id)
                                        .send()
                                        .onSuccess(r -> {
                                            JsonArray array = r.body().toJsonArray();
                                            List<ParameterValue> history = new ArrayList<>();
                                            array.forEach(o -> {
                                                ParameterValue op = gson.fromJson(o.toString(), ParameterValueImpl.class);
                                                history.add(op);
                                            });
                                            this.parameter.setHistory(history);
                                            String status = this.status();
                                            this.view.initializePage(this.parameterName,
                                                    this.min.toString() + unit,
                                                    this.max.toString() + unit,
                                                    this.parameter.getCurrentValue().getValue().toString() + unit,
                                                    this.parameter.getHistoryAsMap(),
                                                    status);
                                        })
                                        .onFailure(System.out::println);
                            });

                });
    }

    private Double paramOptimalValue(String type, String param, Plant plant){
        String original = param.toLowerCase();
        String paramName = original.substring(0, 1).toUpperCase() + original.substring(1);
        try {
            Class<?> c = Class.forName(Plant.class.getName());
            return (Double) c.getDeclaredMethod("get"+type+paramName).invoke(plant);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
