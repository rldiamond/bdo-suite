package module.gardening.model;

public class Crop {

    private String cropName;
    private String seedName;
    private long cropId;
    private long seedId;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public long getCropId() {
        return cropId;
    }

    public void setCropId(long cropId) {
        this.cropId = cropId;
    }

    public long getSeedId() {
        return seedId;
    }

    public void setSeedId(long seedId) {
        this.seedId = seedId;
    }
}
