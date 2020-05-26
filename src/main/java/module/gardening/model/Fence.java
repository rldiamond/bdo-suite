package module.gardening.model;

import common.json.JsonParseException;
import module.gardening.GardeningJsonFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Fence {
    private static final Logger logger = LogManager.getLogger(Fence.class);
    private static final List<Fence> fences;

    static {
        try {
            fences = GardeningJsonFileReader.readFencesFromFile(Fence.class.getResourceAsStream("/module/gardening/Fences.json"));
        } catch (JsonParseException ex) {
            logger.error("Fatal error! Could not parse fence JSON!", ex);
            throw new RuntimeException(ex);
        }
    }

    public List<Fence> getFences() {
        return fences;
    }

    private String name;
    private int grids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrids() {
        return grids;
    }

    public void setGrids(int grids) {
        this.grids = grids;
    }
}
