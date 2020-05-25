package module.barter.model;

import common.json.JsonParseException;
import module.barter.BarterJsonFileReader;
import module.common.ModuleException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Simple POJO used to describe each available Barter level.
 */
public class BarterLevel {

    private static final List<BarterLevel> barterLevels;
    private static final Logger logger = LogManager.getLogger(BarterLevel.class);

    static {
        try {
            barterLevels = BarterJsonFileReader.readBarterLevelsFromFile(BarterLevelType.class.getResourceAsStream("/module/barter/BarterLevels.json"));
        } catch (JsonParseException ex) {
            logger.error("Fatal error! Could not load Barter Levels from JSON!", ex);
            throw new RuntimeException(ex);
        }
    }

    public static List<BarterLevel> getBarterLevels() {
        return barterLevels;
    }

    /**
     * Return BarterLevel information for the supplied BarterLevelType.
     *
     * @param type The type to get information from.
     * @return
     * @throws ModuleException If the BarterLevel does not exist. If this occurs, check that there is an applicable entry in the BarterLevels JSON file.
     */
    public static BarterLevel getBarterLevelByType(BarterLevelType type) {
        return barterLevels.stream().filter(barterLevel -> type.equals(barterLevel.getLevel())).findAny().orElseThrow(RuntimeException::new);
    }

    private BarterLevelType level;
    private double weight;
    private double value;

    public BarterLevel() {

    }

    public BarterLevel(BarterLevelType level, double weight, double value) {
        this.level = level;
        this.weight = weight;
        this.value = value;
    }

    public BarterLevelType getLevel() {
        return level;
    }

    public double getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }
}
