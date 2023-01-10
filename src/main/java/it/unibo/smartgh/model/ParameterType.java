package it.unibo.smartgh.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ParameterType {

    BRIGHTNESS("Luminosità", "brightness.png"),
    SOIL_MOISTURE("Umidità del suolo", "soilMoisture.png"),
    HUMIDITY("Umidità dell'aria", "humidity.png"),
    TEMPERATURE("Temperatura", "temperature.png");

    private final String name;
    private final String imagePath;

    ParameterType(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public static List<String> parameters() {
        return Arrays.stream(values()).map(ParameterType::getName).collect(Collectors.toList());
    }
}
