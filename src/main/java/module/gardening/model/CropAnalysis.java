package module.gardening.model;

public class CropAnalysis {

    private Crop crop;
    private double valuePerHarvest;
    private int seedAvailability;

    public int getSeedAvailability() {
        return seedAvailability;
    }

    public void setSeedAvailability(int seedAvailability) {
        this.seedAvailability = seedAvailability;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public double getValuePerHarvest() {
        return valuePerHarvest;
    }

    public void setValuePerHarvest(double valuePerHarvest) {
        this.valuePerHarvest = valuePerHarvest;
    }
}
