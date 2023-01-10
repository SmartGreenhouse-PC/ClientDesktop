package it.unibo.smartgh.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ParameterType {

    BRIGHTNESS("Luminosità", "brightness"),
    SOIL_MOISTURE("Umidità del suolo", "soilMoisture"),
    HUMIDITY("Umidità dell'aria", "humidity"),
    TEMPERATURE("Temperatura", "temperature");

    private final String title;
    private final String name;

    ParameterType(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return this.name;
    }

    public String getImagePath() {
        return this.name + ".png";
    }

    public static List<String> parameters() {
        return Arrays.stream(values()).map(ParameterType::getName).collect(Collectors.toList());
    }
}
