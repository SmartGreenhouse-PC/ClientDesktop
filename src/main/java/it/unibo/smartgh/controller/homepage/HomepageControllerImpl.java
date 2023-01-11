package it.unibo.smartgh.controller.homepage;

import com.google.gson.Gson;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import it.unibo.smartgh.model.*;
import it.unibo.smartgh.presentation.GsonUtils;
import it.unibo.smartgh.view.homepage.HomepageView;
import it.unibo.smartgh.view.homepage.HomepageViewImpl;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class HomepageControllerImpl implements HomepageController {

    private static final int PORT = 8890;
    private final static int SOCKET_PORT = 1234;
    private static final String HOST = "localhost";
    private final static String SOCKET_HOST = "localhost";
    private final static String BASE_PATH = "/clientCommunication";
    private static final String GREENHOUSE_PATH = BASE_PATH + "/greenhouse";
    private static final String PARAMETER_PATH = BASE_PATH + "/parameter";
    private final HomepageView view;
    private final Vertx vertx;
    private final String id;
    private final Gson gson;
    private GreenhouseImpl greenhouse;
    private Plant plant;
    private Map<String, String> unit;

    public HomepageControllerImpl(HomepageViewImpl homepageView, String id) {
        this.view = homepageView;
        this.id = id;
        this.vertx = Vertx.vertx();
        this.gson = GsonUtils.createGson();
        this.updateView();
        this.setSocket();
    }

    private void setSocket() {
        HttpServer server = vertx.createHttpServer();
        server.webSocketHandler(ctx -> ctx.textMessageHandler(msg -> {
                JsonObject json = new JsonObject(msg);
                if (json.getValue("greenhouseId").equals(this.id)) {
                    Optional<ParameterType> parameter = ParameterType.parameterOf(json.getString("parameterName"));
                    parameter.ifPresent(parameterType -> this.view.updateParameterValue(parameterType,
                            json.getDouble("value"), json.getString("status")));
                }
           })).listen(SOCKET_PORT, SOCKET_HOST);
    }

    private void updateView() {
        WebClient client = WebClient.create(vertx);
        client.get(PORT, HOST, GREENHOUSE_PATH)
                .addQueryParam("id", id)
                .as(BodyCodec.string())
                .send()
                .onSuccess(res -> {
                    this.greenhouse = gson.fromJson(res.body(), GreenhouseImpl.class);
                    this.greenhouse.setId(this.id);
                    this.plant = greenhouse.getPlant();
                    this.unit = plant.getUnitMap();
                    this.view.setPlantInformation(plant.getName(), plant.getDescription(), plant.getImg());
                    this.view.setParameterInfo(ParameterType.BRIGHTNESS, plant.getMinBrightness(),
                            plant.getMaxBrightness(), this.unit.get(ParameterType.BRIGHTNESS.getName()));
                    this.view.setParameterInfo(ParameterType.SOIL_MOISTURE, plant.getMinSoilMoisture(),
                            plant.getMaxSoilMoisture(), this.unit.get(ParameterType.SOIL_MOISTURE.getName()));
                    this.view.setParameterInfo(ParameterType.HUMIDITY, plant.getMinHumidity(), plant.getMaxHumidity(),
                            this.unit.get(ParameterType.HUMIDITY.getName()));
                    this.view.setParameterInfo(ParameterType.TEMPERATURE, plant.getMinTemperature(),
                            plant.getMaxTemperature(), this.unit.get(ParameterType.TEMPERATURE.getName()));
                })
                .onFailure(Throwable::printStackTrace)
                .andThen(res ->
                        Arrays.stream(ParameterType.values()).forEach(p ->
                                client.get(PORT, HOST, PARAMETER_PATH)
                                        .addQueryParam("id", id)
                                        .addQueryParam("parameterName", p.getName())
                                        .as(BodyCodec.string())
                                        .send()
                                        .onSuccess(r -> {
                                            final ParameterValue value = gson.fromJson(r.body(), ParameterValueImpl.class);
                                            boolean inRange = true;
                                            switch (p) {
                                                case BRIGHTNESS -> inRange = value.getValue() < this.plant.getMaxBrightness() && value.getValue() > this.plant.getMinBrightness();
                                                case SOIL_MOISTURE -> inRange = value.getValue() < this.plant.getMaxSoilMoisture() && value.getValue() > this.plant.getMinSoilMoisture();
                                                case HUMIDITY -> inRange = value.getValue() < this.plant.getMaxHumidity() && value.getValue() > this.plant.getMinHumidity();
                                                case TEMPERATURE -> inRange = value.getValue() < this.plant.getMaxTemperature() && value.getValue() > this.plant.getMinTemperature();
                                            }
                                            this.view.updateParameterValue(p, value.getValue(), inRange ? "normal" : "alarm");
                                        })));
    }
}
