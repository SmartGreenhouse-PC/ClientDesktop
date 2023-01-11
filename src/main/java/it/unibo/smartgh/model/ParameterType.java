package it.unibo.smartgh.model;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum ParameterType {

    BRIGHTNESS("Luminosità", "brightness"),
    SOIL_MOISTURE("Umidità del suolo", "soilMoisture"),
    HUMIDITY("Umidità dell'aria", "humidity"),
    TEMPERATURE("Temperatura", "temperature");

    private final String title;
    private final String name;

    ParameterType(String title, String name) {
        this.title = new String(title.getBytes(), StandardCharsets.UTF_8);
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

    public static Optional<ParameterType> parameterOf(String parameterName) {
        return Arrays.stream(ParameterType.values()).filter(p -> p.name.equals(parameterName)).findFirst();
    }

    public static List<String> parameters() {
        return Arrays.stream(values()).map(ParameterType::getName).collect(Collectors.toList());
    }
}
