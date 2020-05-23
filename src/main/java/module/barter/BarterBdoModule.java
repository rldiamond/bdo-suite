package module.barter;

import common.json.JsonParseException;
import module.barter.display.BarterModulePane;
import module.barter.model.BarterGood;
import module.common.BdoModule;
import module.common.ModulePane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * BdoModule for the bartering module.
 */
public class BarterBdoModule extends BdoModule {

    private static final Logger logger = LogManager.getLogger(BarterBdoModule.class);

    private List<BarterGood> barterGoods;

    public BarterBdoModule() {
        super("Bartering", "Calculate optimal routes for your bartering session.");
    }

    /**
     * Initializes the module.
     */
    protected void initialize() {
        // load in the barter goods from the JSON configuration file.
        URL url = BarterModulePane.class.getClassLoader().getResource("barter.json");
        try {
            barterGoods = BarterJsonFileReader.readBarterGoodsFromFile(new File(url.getPath()));
        } catch (JsonParseException ex) {
            logger.error("Fatal error! Could not parse barter goods JSON!", ex);
            throw new RuntimeException(ex);
        }

    }

    @Override
    public ModulePane getModulePane() {
        return new BarterModulePane();
    }
}
