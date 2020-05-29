package module.barter.model;

import common.json.JsonParseException;
import module.barter.BarterJsonFileReader;
import module.common.model.BdoItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * POJO to describe a single barter good.
 */
public class BarterGood extends BdoItem {

    private static final Logger logger = LogManager.getLogger(BarterGood.class);
    private static final List<BarterGood> barterGoods;

    static {
        try {
            barterGoods = BarterJsonFileReader.readBarterGoodsFromFile(BarterGood.class.getResourceAsStream("/module/barter/BarterGoods.json"));
        } catch (JsonParseException ex) {
            logger.error("Fatal error! Could not parse barter goods JSON!", ex);
            throw new RuntimeException(ex);
        }
    }

    public static List<BarterGood> getBarterGoods() {
        return barterGoods;
    }

    public static Optional<BarterGood> getBarterGoodByName(String name) {
        return barterGoods.stream().filter(barterGood -> barterGood.getName().equalsIgnoreCase(name)).findAny();
    }


    private BarterLevelType level;

    public BarterGood() {
    }

    public BarterLevelType getLevelType() {
        return level;
    }

    public BarterLevel getBarterLevel() {
        try {
            return BarterLevel.getBarterLevelByType(getLevelType());
        } catch (Exception ex) {
            return null;
        }
    }

    public void setLevel(BarterLevelType level) {
        this.level = level;
    }

}
