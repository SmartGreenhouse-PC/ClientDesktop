package it.unibo.smartgh.presentation.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unibo.smartgh.model.parameter.ParameterValue;
import it.unibo.smartgh.model.parameter.ParameterValueImpl;
import it.unibo.smartgh.presentation.GsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to verify the correct deserialization of a {@link ParameterValue} and its property.
 */
class ParameterValueDeserializerTest {

    private ParameterValue parameterValue;
    private Gson gson;


    @BeforeEach
    void setUp() {
        this.parameterValue = new ParameterValueImpl("63af0ae025d55e9840cbc1fa", new Date(), 100.0) {
        };
        this.gson = GsonUtils.createGson();
    }

    @Test
    void testDeserialization() {
        JsonObject json = new JsonObject();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        json.addProperty("greenhouseId", parameterValue.getGreenhouseId());
        json.addProperty("date", formatter.format(parameterValue.getDate()));
        json.addProperty("value", parameterValue.getValue());

        ParameterValue deserialized = this.gson.fromJson(json, ParameterValueImpl.class);
        assertEquals(this.parameterValue.getGreenhouseId(), deserialized.getGreenhouseId());
        assertEquals(this.parameterValue.getDate().toString(), deserialized.getDate().toString());
        assertEquals(this.parameterValue.getValue(), deserialized.getValue());
    }
}