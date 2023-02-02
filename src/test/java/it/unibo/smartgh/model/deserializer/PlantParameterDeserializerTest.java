package it.unibo.smartgh.model.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unibo.smartgh.model.plant.PlantParameter;
import it.unibo.smartgh.model.plant.PlantParameterBuilder;
import it.unibo.smartgh.model.plant.PlantParameterImpl;
import it.unibo.smartgh.presentation.GsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test to verify the correct deserialization of a {@link Parameter} and its property.
 */
class PlantParameterDeserializerTest {

    private PlantParameter parameter;
    private Gson gson;

    @BeforeEach
    void setUp() {
        this.parameter = new PlantParameterBuilder("brightness").min(0.0).max(1000.0).unit("lux").build();
        this.gson = GsonUtils.createGson();
    }

    private JsonObject serializeBrightnessParameter(){
        JsonObject brightnessParameter = new JsonObject();
        brightnessParameter.addProperty("name", this.parameter.getName());
        brightnessParameter.addProperty("min", this.parameter.getMin());
        brightnessParameter.addProperty("max", this.parameter.getMax());
        brightnessParameter.addProperty("unit", this.parameter.getUnit());
        return brightnessParameter;
    }

    @Test
    void testDeserialization() {
        PlantParameter deserialized = this.gson.fromJson(this.serializeBrightnessParameter(), PlantParameterImpl.class);
        assertEquals(this.parameter.getName(), deserialized.getName());
        assertEquals(this.parameter.getMin(), deserialized.getMin());
        assertEquals(this.parameter.getMax(), deserialized.getMax());
        assertEquals(this.parameter.getUnit(), deserialized.getUnit());
    }
}