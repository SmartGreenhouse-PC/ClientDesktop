package it.unibo.smartgh.presentation.deserializer;

import com.google.gson.*;
import it.unibo.smartgh.model.greenhouse.Greenhouse;
import it.unibo.smartgh.model.greenhouse.GreenhouseImpl;
import it.unibo.smartgh.model.greenhouse.Modality;
import it.unibo.smartgh.model.plant.Plant;
import it.unibo.smartgh.model.plant.PlantImpl;

import java.lang.reflect.Type;

/**
 * The implementation of the Greenhouse deserializer.
 */
public class GreenhouseDeserializer  extends GeneralDeserializer implements JsonDeserializer<Greenhouse> {
    @Override
    public Greenhouse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Greenhouse greenhouse;
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
