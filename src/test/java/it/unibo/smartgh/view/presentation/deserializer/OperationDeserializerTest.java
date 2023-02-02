package it.unibo.smartgh.view.presentation.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.vertx.core.json.Json;
import it.unibo.smartgh.model.greenhouse.Modality;
import it.unibo.smartgh.model.operation.*;
import it.unibo.smartgh.presentation.GsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to verify the correct deserialization of a {@link Operation} and its property.
 */
class OperationDeserializerTest {

    private Operation operation;
    private Gson gson;

    @BeforeEach
    void setUp() {
        this.operation = new OperationImpl("63af0ae025d55e9840cbc1fa", Modality.AUTOMATIC, new Date(), "brightness", "increase");
        this.gson = GsonUtils.createGson();
    }

    @Test
    void testDeserialization() {
        JsonObject json = new JsonObject();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        json.addProperty("greenhouseId", this.operation.getGreenhouseId());
        json.addProperty("modality", this.operation.getModality().toString());
        json.addProperty("date", formatter.format(this.operation.getDate()));
        json.addProperty("parameter", this.operation.getParameter());
        json.addProperty("action", this.operation.getAction());

        Operation deserialized = this.gson.fromJson(json, OperationImpl.class);
        assertEquals(this.operation.getGreenhouseId(), deserialized.getGreenhouseId());
        assertEquals(this.operation.getModality(), deserialized.getModality());
        assertEquals(this.operation.getDate().toString(), deserialized.getDate().toString());
        assertEquals(this.operation.getParameter(), deserialized.getParameter());
        assertEquals(this.operation.getAction(), deserialized.getAction());
    }
}