package it.unibo.smartgh.presentation.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * This class represents general deserialization operations.
 */
public abstract class GeneralDeserializer {

    /**
     * Gets a property of the object as a string.
     * @param object the object that contains the property
     * @param name the name of the property
     * @return the property as a string
     */
    public String getPropertyAsString(JsonObject object, String name) {
        if (object.has(name)) {
            JsonElement value = object.get(name);
            if (value.isJsonNull()) return null;
            return value.getAsString();
        }
        return null;
    }

    /**
     * Gets a property of the object as the specified type.
     * @param object the object that contains the property
     * @param name the name of the property
     * @param type the type to convert
     * @param context the {@link JsonDeserializationContext}
     * @param <T> the class type
     * @return the property as the specified type
     */
    public <T> T getPropertyAs(JsonObject object, String name, Class<T> type,  JsonDeserializationContext context) {
        if (object.has(name)) {
            JsonElement value = object.get(name);
            if (value.isJsonNull()) return null;
            return context.deserialize(value, type);
        }
        return null;
    }
}

