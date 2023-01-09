package it.unibo.smartgh.controller;

import com.google.gson.Gson;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetServer;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import it.unibo.smartgh.model.*;
import it.unibo.smartgh.view.ParameterPageView;
import it.unibo.smartgh.view.ParameterPageViewImpl;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class ParameterPageControllerImpl implements ParameterPageController {
    private final static int PORT = 8889; //TODO to be change
    private final static String HOST = "localhost";
    private final String id;
    private final String parameterName;
    private final Vertx vertx;
    private final Gson gson;
    private Parameter parameter;
    private Double min;
    private Double max;

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
                if (json.getValue("parameterName").equals(parameterName)) {
                    this.parameter.getCurrentValue().setValue(Double.valueOf(json.getValue("value").toString()));
                    this.view.setCurrentValue(json.getValue("value").toString(), this.status());
                }
            });
        }).listen(1234, "localhost");

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
        //TODO cambiare la richiesta con quella al servizio gestione client
        client.get(PORT, HOST, "/greenhouse")
                .addQueryParam("id", id)
                .as(BodyCodec.string())
                .send()
                .onSuccess(resp -> {
                    Greenhouse greenhouse = gson.fromJson(resp.body(), GreenhouseImpl.class);
                    this.min = this.paramOptimalValue("Min", this.parameterName, greenhouse.getPlant());
                    this.max = this.paramOptimalValue("Max", this.parameterName, greenhouse.getPlant());
                })
                .onFailure(System.out::println)
                .andThen( resp -> {
                    client.get(8895, HOST, "/"+this.parameterName)
                            .addQueryParam("id", id)
                            .as(BodyCodec.string())
                            .send()
                            .onSuccess(r -> {
                                ParameterValue val = gson.fromJson(r.body(), ParameterValueImpl.class);
                                this.parameter = new ParameterImpl(this.parameterName, val, new ArrayList<>());
                                String status = this.status();
                                this.view.initializePage(this.parameterName,
                                        this.min.toString(),
                                        this.max.toString(),
                                        this.parameter.getCurrentValue().getValue().toString(),
                                        status);
                            })
                            .onFailure(System.out::println);
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
