package module.barter.model;

import common.json.JsonParseException;
import module.barter.BarterJsonFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * POJO to describe a single barter good.
 */
public class BarterGood {

    private static final Logger logger = LogManager.getLogger(BarterGood.class);
    private static final List<BarterGood> barterGoods;

    static {
        URL goodsUrl = BarterGood.class.getClassLoader().getResource("module/barter/BarterGoods.json");
        try {
            barterGoods = BarterJsonFileReader.readBarterGoodsFromFile(new File(goodsUrl.getPath()));
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


    private String name;
    private BarterLevelType level;
    private long itemId;

    public BarterGood() {
    }

    public BarterGood(String name, BarterLevelType level, long itemId) {
        this.name = name;
        this.level = level;
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BarterLevelType getLevel() {
        return level;
    }

    public void setLevel(BarterLevelType level) {
        this.level = level;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
