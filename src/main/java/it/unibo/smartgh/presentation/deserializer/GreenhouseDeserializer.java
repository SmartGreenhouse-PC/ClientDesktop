package it.unibo.smartgh.presentation.deserializer;

import com.google.gson.*;
import it.unibo.smartgh.model.*;

import java.lang.reflect.Type;

public class GreenhouseDeserializer  extends GeneralDeserializer implements JsonDeserializer<Greenhouse> {
    @Override
    public Greenhouse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Greenhouse greenhouse = null;
        if(json instanceof JsonObject){
            JsonObject object = (JsonObject) json;
            Plant plant = this.getPropertyAs(object, "plant", PlantImpl.class, context);
            Modality modality = Modality.valueOf(this.getPropertyAsString(object, "modality"));
            greenhouse = new GreenhouseImpl(plant, modality);
        }else{
            throw new JsonParseException("Not a valid greenhouse");
        }
        return greenhouse;
    }
}
