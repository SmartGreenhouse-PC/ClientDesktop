package it.unibo.smartgh.controller.homepage;

import com.google.gson.Gson;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import it.unibo.smartgh.model.Greenhouse;
import it.unibo.smartgh.model.GreenhouseImpl;
import it.unibo.smartgh.model.ParameterType;
import it.unibo.smartgh.model.Plant;
import it.unibo.smartgh.presentation.GsonUtils;
import it.unibo.smartgh.view.homepage.HomepageView;
import it.unibo.smartgh.view.homepage.HomepageViewImpl;

public class HomepageControllerImpl implements HomepageController {

    private static final int PORT = 8890;
    private static final String HOST = "localhost";
    private final static String BASE_PATH = "/clientCommunication";
    private static final String GREENHOUSE_PATH = "/greenhouse";
    private final HomepageView view;
    private final Vertx vertx;
    private final String id;
    private final Gson gson;

    public HomepageControllerImpl(HomepageViewImpl homepageView, String id) {
        this.view = homepageView;
        this.id = id;
        this.vertx = Vertx.vertx();
        this.gson = GsonUtils.createGson();
        this.updateView();
    }

    private void updateView() {
        WebClient client = WebClient.create(vertx);
        client.get(PORT, HOST, BASE_PATH + GREENHOUSE_PATH)
                .addQueryParam("id", id)
                .as(BodyCodec.string())
                .send()
                .onSuccess(res -> {
                    Greenhouse greenhouse = gson.fromJson(res.body(), GreenhouseImpl.class);
                    greenhouse.setId(this.id);
                    Plant plant = greenhouse.getPlant();
                    this.view.setPlantInformation(plant.getName(), plant.getDescription(), plant.getImg());
                    this.view.setParameterInfo(ParameterType.BRIGHTNESS, plant.getMinBrightness(),
                            plant.getMaxBrightness());
                    this.view.setParameterInfo(ParameterType.SOIL_MOISTURE, plant.getMinSoilMoisture(),
                            plant.getMaxSoilMoisture());
                    this.view.setParameterInfo(ParameterType.HUMIDITY, plant.getMinHumidity(), plant.getMaxHumidity());
                    this.view.setParameterInfo(ParameterType.TEMPERATURE, plant.getMinTemperature(), plant.getMaxTemperature());
                })
                .onFailure(Throwable::printStackTrace);
    }
}
