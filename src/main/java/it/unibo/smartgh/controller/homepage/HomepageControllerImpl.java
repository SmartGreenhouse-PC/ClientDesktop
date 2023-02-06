package it.unibo.smartgh.controller.homepage;

import com.google.gson.Gson;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.WebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import it.unibo.smartgh.model.greenhouse.GreenhouseImpl;
import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.model.parameter.ParameterValue;
import it.unibo.smartgh.model.parameter.ParameterValueImpl;
import it.unibo.smartgh.model.plant.Plant;
import it.unibo.smartgh.model.plant.PlantParameter;
import it.unibo.smartgh.presentation.GsonUtils;
import it.unibo.smartgh.view.homepage.HomepageView;
import it.unibo.smartgh.view.homepage.HomepageViewImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * The implementation of {@link HomepageController} interface.
 */
public class HomepageControllerImpl implements HomepageController {

    private static final int PORT = 8890;
    private static int SOCKET_PORT;
    private static String HOST;
    private final static String BASE_PATH = "/clientCommunication";
    private static final String GREENHOUSE_PATH = BASE_PATH + "/greenhouse";
    private static final String PARAMETER_PATH = BASE_PATH + "/parameter";
    private final HomepageView view;
    private final Vertx vertx;
    private final String id;
    private final Gson gson;
    private GreenhouseImpl greenhouse;
    private Plant plant;
    WebSocket socket;

    /**
     * Instantiates a new Homepage controller.
     * @param homepageView the homepage view
     * @param id           the greenhouse id
     */
    public HomepageControllerImpl(HomepageViewImpl homepageView, String id) {
        this.view = homepageView;
        this.id = id;
        this.vertx = Vertx.vertx();
        this.gson = GsonUtils.createGson();
        try {
            InputStream is = HomepageControllerImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            HOST = properties.getProperty("host");
            SOCKET_PORT = Integer.parseInt(properties.getProperty("socketParameter.port"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initializeData() {
        this.updateView();
        this.setSocket();
    }

    @Override
    public void closeSocket() {
        this.socket.close();
    }

    private void setSocket() {
        HttpClient socketClient = vertx.createHttpClient();
        socketClient.webSocket(SOCKET_PORT,
                HOST,
                "/",
                wsC -> {
                    WebSocket ctx = wsC.result();
                    if (ctx != null) {
                        socket = ctx;
                        ctx.textMessageHandler(msg -> {
                            JsonObject json = new JsonObject(msg);
                            if (json.getValue("greenhouseId").equals(this.id)) {
                                Optional<ParameterType> parameter = ParameterType.parameterOf(json.getString("parameterName"));
                                parameter.ifPresent(parameterType -> this.view.updateParameterValue(parameterType,
                                        json.getDouble("value"), json.getString("status")));
                            }
                        });
                    }});

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
                    this.view.setPlantInformation(plant.getName(), plant.getDescription(), plant.getImg());
                    plant.getParameters().forEach((k,v) -> {
                        this.view.setParameterInfo(k,
                                v.getMin(),
                                v.getMax(),
                                v.getUnit());
                    });

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
                                            boolean inRange = value.getValue() <= this.plant.getParameters().get(p).getMax()
                                                    && value.getValue() >= this.plant.getParameters().get(p).getMin();
                                            this.view.updateParameterValue(p, value.getValue(), inRange ? "normal" : "alarm");
                                        })));
    }
}
