package module.barter;

import common.json.JsonParseException;
import module.barter.display.BarterModulePane;
import module.barter.model.BarterGood;
import module.barter.model.BarterLevel;
import module.barter.model.Barter;
import module.common.BdoModule;
import module.common.ModuleException;
import module.common.ModulePane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * BdoModule for the bartering module.
 */
public class BarterBdoModule extends BdoModule {

    private static final Logger logger = LogManager.getLogger(BarterBdoModule.class);
    private List<BarterGood> barterGoods;
    private List<BarterLevel> barterLevels;
    private BarterModulePane barterModulePane;

    public BarterBdoModule() throws ModuleException {
        super("Bartering", "Calculate optimal routes for your bartering session.");
    }

    /**
     * Initializes the module.
     */
    protected void initialize() throws ModuleException {
        // build the barter module pane
        barterModulePane = new BarterModulePane();

        //TODO: This is temporary logic for testing purposes, will be reworked into a GUI
        URL barterUrl = BarterModulePane.class.getClassLoader().getResource("barter.json");
        List<Barter> possibleRoutes;
        try {
            possibleRoutes = BarterJsonFileReader.readBarterRoutesFromFile(new File(barterUrl.getPath()));
        } catch (JsonParseException ex) {
            logger.error("Fatal error! Could not parse the possible barter routes JSON!", ex);
            throw new ModuleException();
        }
        barterModulePane.setBarters(possibleRoutes);

    }

    /**
     * @inheritDoc
     */
    @Override
    public ModulePane getModulePane() {
        return barterModulePane;
    }
}
