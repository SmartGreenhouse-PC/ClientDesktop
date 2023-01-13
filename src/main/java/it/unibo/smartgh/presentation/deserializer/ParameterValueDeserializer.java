package it.unibo.smartgh.presentation.deserializer;

import com.google.gson.*;
import it.unibo.smartgh.model.parameter.ParameterValue;
import it.unibo.smartgh.model.parameter.ParameterValueImpl;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The implementation of Parameter value deserializer.
 */
public class ParameterValueDeserializer  extends GeneralDeserializer implements JsonDeserializer<ParameterValue> {

    @Override
    public ParameterValue deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        ParameterValue plantValue = new ParameterValueImpl();
        if(json instanceof JsonObject){
            JsonObject object = (JsonObject) json;
            plantValue.setGreenhouseId(this.getPropertyAsString(object, "greenhouseId"));
            try {
                plantValue.setDate(formatter.parse(this.getPropertyAsString(object, "date")));
            } catch (ParseException e) {
                throw new JsonParseException("Not a valid date format (dd/MM/yyyy - HH:mm:ss)");
            }
            plantValue.setValue(this.getPropertyAs(object, "value", Double.class, context));
        }else{
            throw new JsonParseException("Not a valid plant value: " + plantValue);
        }

        return plantValue;
    }
}
