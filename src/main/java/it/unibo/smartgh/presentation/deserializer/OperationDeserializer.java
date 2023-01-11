package it.unibo.smartgh.presentation.deserializer;

import com.google.gson.*;
import it.unibo.smartgh.model.Modality;
import it.unibo.smartgh.model.Operation;
import it.unibo.smartgh.model.OperationImpl;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Custom {@link JsonDeserializer} for the {@link Operation} class. Used to convert a JSON object
 * into an {@link Operation} object.
 */
public class OperationDeserializer extends GeneralDeserializer implements JsonDeserializer<Operation> {

    @Override
    public Operation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        Operation operation = new OperationImpl();
        if (json instanceof JsonObject) {
            JsonObject object = (JsonObject) json;
            operation.setGreenhouseId(this.getPropertyAsString(object, "greenhouseId"));
            try {
                operation.setDate(formatter.parse(this.getPropertyAsString(object, "date")));
            } catch (ParseException e) {
                throw new JsonParseException("Not a valid date format (dd/MM/yyyy - HH:mm:ss)");
            }
            operation.setModality(Modality.valueOf(this.getPropertyAsString(object, "modality").toUpperCase(Locale.ROOT)));
            operation.setParameter(this.getPropertyAsString(object, "parameter"));
            operation.setAction(this.getPropertyAsString(object, "action"));
        } else {
            throw new JsonParseException("Not a valid operation: " + operation);
        }
        return operation;
    }
}
