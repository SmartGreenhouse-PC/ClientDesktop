package it.unibo.smartgh.model;

/**
 * Implementation of the plant entity
 */
public class PlantImpl implements Plant{
    private final String name;
    private final String description;
    private final String img;
    private final Double minTemperature;
    private final Double maxTemperature;
    private final Double minBrightness;
    private final Double maxBrightness;
    private final Double minSoilMoisture;
    private final Double maxSoilMoisture;
    private final Double minHumidity;
    private final Double maxHumidity;

    /**
     * Constructor of the plant entity
     * @param name of the plant
     * @param description of the plant
     * @param img of the plant
     * @param minTemperature of the plant
     * @param maxTemperature of the plant
     * @param minBrightness of the plant
     * @param maxBrightness of the plant
     * @param minSoilMoisture of the plant
     * @param maxSoilMoisture of the plant
     * @param minHumidity of the plant
     * @param maxHumidity of the plant
     */
    public PlantImpl(String name,
                     String description,
                     String img,
                     Double minTemperature,
                     Double maxTemperature,
                     Double minBrightness,
                     Double maxBrightness,
                     Double minSoilMoisture,
                     Double maxSoilMoisture,
                     Double minHumidity,
                     Double maxHumidity) {
        this.name = name;
        this.description = description;
        this.img = img;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minBrightness = minBrightness;
        this.maxBrightness = maxBrightness;
        this.minSoilMoisture = minSoilMoisture;
        this.maxSoilMoisture = maxSoilMoisture;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Double getMinTemperature() {
        return this.minTemperature;
    }

    @Override
    public Double getMaxTemperature() {
        return this.maxTemperature;
    }

    @Override
    public Double getMinBrightness() {
        return this.minBrightness;
    }

    @Override
    public Double getMaxBrightness() {
        return this.maxBrightness;
    }

    @Override
    public Double getMinSoilMoisture() {
        return this.minSoilMoisture;
    }

    @Override
    public Double getMaxSoilMoisture() {
        return this.maxSoilMoisture;
    }

    @Override
    public Double getMinHumidity() {
        return this.minHumidity;
    }

    @Override
    public Double getMaxHumidity() {
        return this.maxHumidity;
    }

    @Override
    public String getImg() {
        return this.img;
    }
}
