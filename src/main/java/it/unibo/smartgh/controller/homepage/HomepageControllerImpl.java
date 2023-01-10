package it.unibo.smartgh.controller.homepage;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import it.unibo.smartgh.view.homepage.HomepageView;
import it.unibo.smartgh.view.homepage.HomepageViewImpl;

public class HomepageControllerImpl implements HomepageController {

    private static final int PORT = 8888;
    private static final String HOST = "localhost";
    private final HomepageView view;
    private final Vertx vertx;
    private final String id;

    public HomepageControllerImpl(HomepageViewImpl homepageView, String id) {
        this.view = homepageView;
        this.id = id;
        this.vertx = Vertx.vertx();
    }

    private void updateView() {
        WebClient client = WebClient.create(vertx);
        client.get(PORT, HOST, "clientCommunication/greenhouse")
                .addQueryParam("id", id)
                .as(BodyCodec.string())
                .send()
                .onSuccess(res -> {
                    System.out.println(res.body()); //todo
                })
                .onFailure(Throwable::printStackTrace);
    }
}
