package it.unibo.smartgh.model;

/**
 * Implementation of the greenhouse entity.
 */
public class GreenhouseImpl implements Greenhouse{
    private final Plant plant;
    private final String id;
    private Modality modality;

    /**
     * Constructor for the greenhouse entity.
     * @param plant of the greenhouse
     * @param modality the actual modality of management
     */
    public GreenhouseImpl(String id, Plant plant, Modality modality) {
        this.plant = plant;
        this.modality = modality;
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Plant getPlant() {
        return this.plant;
    }

    @Override
    public Modality getActualModality() {
        return this.modality;
    }
}
