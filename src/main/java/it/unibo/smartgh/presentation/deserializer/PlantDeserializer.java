package it.unibo.smartgh.presentation.deserializer;

import com.google.gson.*;
import it.unibo.smartgh.model.*;

import java.lang.reflect.Type;

public class PlantDeserializer  extends GeneralDeserializer implements JsonDeserializer<Plant> {
    @Override
    public Plant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Plant plant = null;
        if(json instanceof JsonObject){
            JsonObject object = (JsonObject) json;
            plant = new PlantBuilder(this.getPropertyAsString(object, "name"))
                    .description(this.getPropertyAsString(object, "description"))
                    .image(this.getPropertyAsString(object, "img"))
                    .minTemperature(this.getPropertyAs(object, "minTemperature", Double.class, context))
                    .maxTemperature(this.getPropertyAs(object, "maxTemperature", Double.class, context))
                    .minBrightness(this.getPropertyAs(object, "minBrightness", Double.class, context))
                    .maxBrightness(this.getPropertyAs(object, "maxBrightness", Double.class, context))
                    .minHumidity(this.getPropertyAs(object, "minHumidity", Double.class, context))
                    .maxHumidity(this.getPropertyAs(object, "maxHumidity", Double.class, context))
                    .minSoilMoisture(this.getPropertyAs(object, "minSoilMoisture", Double.class, context))
                    .maxSoilMoisture(this.getPropertyAs(object, "maxSoilMoisture", Double.class, context))
                    .build();
        }else{
            throw new JsonParseException("Not a valid plant");
        }
        return plant;
    }
}
