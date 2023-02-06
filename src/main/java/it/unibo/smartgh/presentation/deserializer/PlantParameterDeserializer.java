package it.unibo.smartgh.presentation.deserializer;

import com.google.gson.*;
import it.unibo.smartgh.model.plant.PlantParameter;
import it.unibo.smartgh.model.plant.PlantParameterBuilder;

import java.lang.reflect.Type;
/**
 * Custom {@link JsonDeserializer} for the {@link PlantParameter} class. Used to convert a {@link PlantParameter} object
 * into an JSON object.
 */
public class PlantParameterDeserializer extends GeneralDeserializer implements JsonDeserializer<PlantParameter> {
    @Override
    public PlantParameter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json instanceof JsonObject){
            JsonObject object = (JsonObject) json;
            String name = this.getPropertyAsString(object, "name");
            Double min = this.getPropertyAs(object, "min", Double.class, context);
            Double max = this.getPropertyAs(object, "max", Double.class, context);
            String unit = this.getPropertyAsString(object, "unit");

            return new PlantParameterBuilder(name).min(min).max(max).unit(unit).build();
        }else {
            throw new JsonParseException("Not a valid parameter: ");
        }
    }
}
