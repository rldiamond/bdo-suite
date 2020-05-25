package module.gardening.model;

import common.json.JsonParseException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import module.common.model.BdoItem;
import module.gardening.GardeningJsonFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.List;


public class Crop extends BdoItem {

    private static final Logger logger = LogManager.getLogger(Crop.class);
    private static final List<Crop> crops;

    static {
        URL cropsUrl = Crop.class.getResource("/module/gardening/Crops.json");
        try {
            crops = GardeningJsonFileReader.readCropsFromFile(Crop.class.getResourceAsStream("/module/gardening/Crops.json"));
        } catch (JsonParseException ex) {
            logger.error("Fatal error! Could not parse crops JSON!", ex);
            throw new RuntimeException(ex);
        }
    }

    public static List<Crop> getCrops() {
        return crops;
    }

    private String cropName;
    private String seedName;
    private long cropId;
    private long seedId;
    private int grids;
    private BooleanProperty cropLoaded = new SimpleBooleanProperty(false);
    private BooleanProperty seedLoaded = new SimpleBooleanProperty(false);
    private BooleanProperty loaded = new SimpleBooleanProperty(false);

    public Crop() {
        loaded.bind(Bindings.and(cropLoaded, seedLoaded));
    }

    public int getGrids() {
        return grids;
    }

    public void setGrids(int grids) {
        this.grids = grids;
    }

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

    public void setSeedLoaded(boolean loaded) {
        seedLoaded.setValue(loaded);
    }

    public void setCropLoaded(boolean loaded) {
        cropLoaded.setValue(loaded);
    }

    public BooleanProperty loadedProperty() {
        return loaded;
    }

    @Override
    public String getName() {
        return getCropName();
    }

    @Override
    public long getItemId() {
        return getCropId();
    }
}
