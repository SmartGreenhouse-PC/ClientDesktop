package it.unibo.smartgh.presentation.deserializer;

import com.google.gson.*;
import it.unibo.smartgh.model.parameter.ParameterType;
import it.unibo.smartgh.model.plant.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of the Plant deserializer.
 */
public class PlantDeserializer  extends GeneralDeserializer implements JsonDeserializer<Plant> {

    @Override
    public Plant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Plant plant;
        if(json instanceof JsonObject){
            JsonObject object = (JsonObject) json;
            String name = this.getPropertyAsString(object, "name");
            String description = this.getPropertyAsString(object, "description");
            String img = this.getPropertyAsString(object, "img");
            Map<ParameterType, PlantParameter> parameters = new HashMap<>();
            object.getAsJsonArray("parameters").forEach(p ->{
                PlantParameter param = context.deserialize(p, PlantParameterImpl.class);
                parameters.put(ParameterType.parameterOf(param.getName()).get(), param);
            });
            plant =  new PlantImpl(name, description, img, parameters);
        }else{
            throw new JsonParseException("Not a valid plant");
        }
        return plant;
    }
}
