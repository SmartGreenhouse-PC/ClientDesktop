package it.unibo.smartgh.controller.operation;

import com.google.gson.Gson;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.WebSocket;
import io.vertx.core.json.JsonArray;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import it.unibo.smartgh.model.greenhouse.Greenhouse;
import it.unibo.smartgh.model.greenhouse.GreenhouseImpl;
import it.unibo.smartgh.model.operation.Operation;
import it.unibo.smartgh.model.operation.OperationImpl;
import it.unibo.smartgh.presentation.GsonUtils;
import it.unibo.smartgh.view.operation.OperationPageView;
import it.unibo.smartgh.view.operation.OperationPageViewImpl;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * The implementation of {@link OperationPageController} interface.
 */
public class OperationPageControllerImpl implements OperationPageController {

    private final static int PORT = 8890;
    private static int SOCKET_PORT;
    private static String HOST;
    private final static String GREENHOUSE_PATH = "/clientCommunication/greenhouse";
    private final static String BASE_PATH = "/clientCommunication/operations";
    private final static String PARAM_PATH = BASE_PATH + "/parameter";
    private final static String DATE_PATH = BASE_PATH + "/date";
    private final static int LIMIT = 100;
    private final Vertx vertx;
    private final String id;
    private final Gson gson;

    private final OperationPageView view;
    private final WebClient client;
    private String selectedParameterFilter = "-";
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private WebSocket socket;

    /**
     * Instantiates a new Operation page controller.
     * @param operationPageView the operation page view
     * @param id                the id
     */
    public OperationPageControllerImpl(OperationPageViewImpl operationPageView, String id) {
        this.view = operationPageView;
        this.id = id;
        this.vertx = Vertx.vertx();
        this.gson = GsonUtils.createGson();
        this.client = WebClient.create(vertx);
        try {
            InputStream is = OperationPageControllerImpl.class.getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(is);
            HOST = properties.getProperty("host");
            SOCKET_PORT = Integer.parseInt(properties.getProperty("socketOperation.port"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            this.dateFrom = null;
            this.dateTo = null;
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
                        this.view.sortTable();
                    })
                    .onFailure(System.out::println);
        } else {
            this.populateTableWithAllOperations();
        }
    }

    @Override
    public void selectRange(LocalDate from, LocalDate to) {
        this.dateFrom = from;
        this.dateTo = to;
        client.get(PORT, HOST, DATE_PATH)
                .addQueryParam("id", id)
                .addQueryParam("limit", String.valueOf(LIMIT))
                .addQueryParam("from", from.toString())
                .addQueryParam("to", to.toString())
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
                    this.view.sortTable();
                })
                .onFailure(System.out::println);
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
                                if (this.dateTo == null && this.dateFrom == null){
                                    if (this.selectedParameterFilter.equals("-")){
                                        this.populateTableWithAllOperations();
                                    } else {
                                        this.changeSelectedParameter(this.selectedParameterFilter);
                                    }
                                }
                            } else {
                                this.selectRange(this.dateFrom, this.dateTo);
                            }
                        });
                    }
                });
    }

    private void initializeView() {
        this.client.get(PORT, HOST, GREENHOUSE_PATH)
                .addQueryParam("id", id)
                .as(BodyCodec.string())
                .send()
                .onSuccess(resp -> {
                    Greenhouse greenhouse = gson.fromJson(resp.body(), GreenhouseImpl.class);
                    greenhouse.setId(this.id);
                    this.view.initializeView(new ArrayList<>(greenhouse.getPlant().getUnitMap().keySet()));
                })
                .onFailure(System.out::println)
                .andThen(r -> populateTableWithAllOperations());

    }

    private void populateTableWithAllOperations() {
        client.get(PORT, HOST, BASE_PATH)
                .addQueryParam("id", id)
                .addQueryParam("limit", String.valueOf(LIMIT))
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
                    this.view.sortTable();
                })
                .onFailure(System.out::println);
    }
}
