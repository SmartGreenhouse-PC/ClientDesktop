package it.unibo.smartgh.controller.parameter;

import com.google.gson.Gson;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.WebSocket;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import it.unibo.smartgh.model.greenhouse.Greenhouse;
import it.unibo.smartgh.model.greenhouse.GreenhouseImpl;
import it.unibo.smartgh.model.parameter.*;
import it.unibo.smartgh.model.plant.Plant;
import it.unibo.smartgh.model.plant.PlantParameter;
import it.unibo.smartgh.presentation.GsonUtils;
import it.unibo.smartgh.view.parameter.ParameterPageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * The implementation of {@link ParameterPageController} interface.
 */
public class ParameterPageControllerImpl implements ParameterPageController {
    private final static int PORT = 8890;
    private static int SOCKET_PORT;
    private static String HOST;
    private final static String BASE_PATH = "/clientCommunication";
    private static final String GREENHOUSE_PATH = BASE_PATH + "/greenhouse";
    private static final String PARAMETER_PATH = BASE_PATH + "/parameter";
    private static final String PARAMETER_HISTORY_PATH = PARAMETER_PATH + "/history";

    private final Vertx vertx;
    private final Gson gson;
    private final String id;
    private ParameterType parameterType;
    private Parameter parameter;
    private Double min;
    private Double max;
    private String unit;
    private final ParameterPageView view;
    private WebSocket socket;

    /**
     * Instantiates a new Parameter page controller.
     *
     * @param parameterPageView the parameter page view
     * @param id                the id of the greenhouse
     */
    public ParameterPageControllerImpl(ParameterPageView parameterPageView, String id) {
        this.view = parameterPageView;
        this.id = id;
        this.gson = GsonUtils.createGson();
        this.vertx = Vertx.vertx();
        try {
            InputStream is = ParameterPageControllerImpl.class.getResourceAsStream("/config.properties");
            var properties = new Properties();
            properties.load(is);
            HOST = properties.getProperty("host");
            SOCKET_PORT = Integer.parseInt(properties.getProperty("socketParameter.port"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ParameterPageView getView() {
        return this.view;
    }

    @Override
    public void setParameter(ParameterType parameter) {
        this.parameterType = parameter;
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
                            if(json.getValue("greenhouseId").equals(this.id)) {
                                if (json.getValue("parameterName").equals(parameterType.getName())) {
                                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                                    Date date;
                                    Double value = Double.valueOf(json.getValue("value").toString());
                                    try {
                                        date = formatter.parse(json.getString("date"));
                                    } catch (ParseException e) {
                                        throw new RuntimeException(e);
                                    }
                                    this.parameter.setCurrentValue(new ParameterValueImpl(this.id, date, value));
                                    var newHistory = this.parameter.getHistory();
                                    newHistory.remove(0);
                                    ParameterValue newParamValue = new ParameterValueImpl(this.id, this.parameter.getCurrentValue().getDate(), this.parameter.getCurrentValue().getValue());
                                    newHistory.add(newParamValue);
                                    this.parameter.setHistory(newHistory);
                                    this.view.updateValues(json.getValue("value").toString() + " " + unit, this.status(), this.parameter.getHistoryAsMap());
                                }
                            }
                        });
                    }

                });

    }

    private String status(){
        return this.parameter.getCurrentValue().getValue().compareTo(this.min) >=0 &&
                this.parameter.getCurrentValue().getValue().compareTo(this.max) <=0 ? "normal" : "alarm";
    }

    private void updateView(){
        String parameterName = this.parameterType.getName();
        WebClient client = WebClient.create(vertx);
        client.get(PORT, HOST, GREENHOUSE_PATH)
                .addQueryParam("id", id)
                .as(BodyCodec.string())
                .send()
                .onSuccess(resp -> {
                    Greenhouse greenhouse = gson.fromJson(resp.body(), GreenhouseImpl.class);
                    greenhouse.setId(this.id);
                    PlantParameter param = greenhouse.getPlant().getParameters().get(ParameterType.parameterOf(parameterName).get());
                    this.unit = param.getUnit();
                    this.min = param.getMin();
                    this.max = param.getMax();
                })
                .onFailure(System.out::println)
                .andThen( resp ->
                        client.get(PORT, HOST, PARAMETER_PATH)
                            .addQueryParam("id", id)
                            .addQueryParam("parameterName", parameterName)
                            .as(BodyCodec.string())
                            .send()
                            .onSuccess(r -> {
                                ParameterValue val = gson.fromJson(r.body(), ParameterValueImpl.class);
                                this.parameter = new ParameterImpl(parameterName, val);
                            })
                            .onFailure(System.out::println)
                            .andThen(resp2 ->
                                    client.get(PORT, HOST,PARAMETER_HISTORY_PATH)
                                        .addQueryParam("limit","15")
                                        .addQueryParam("parameterName", parameterName)
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
                                            this.view.initializePage(this.parameterType,
                                                    this.min.toString() + unit,
                                                    this.max.toString() + unit,
                                                    this.parameter.getCurrentValue().getValue().toString() + unit,
                                                    this.parameter.getHistoryAsMap(),
                                                    status);
                                        })
                                        .onFailure(System.out::println)));
    }
}
