package module.barter.display;

import common.json.JsonParseException;
import javafx.scene.Node;
import module.barter.BarterBdoModule;
import module.barter.BarterJsonFileReader;
import module.barter.model.Barter;
import module.display.ToolView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.List;

public class RouteOptimizationToolView extends ToolView {

    private static final Logger logger = LogManager.getLogger(BarterBdoModule.class);

    public RouteOptimizationToolView() {
        super("Route Optimization");
    }

    @Override
    public Node getDisplay() {
        BarterModulePane barterModulePane = new BarterModulePane();

        //TODO: This is temporary logic for testing purposes, will be reworked into a GUI
        URL barterUrl = BarterModulePane.class.getClassLoader().getResource("barter.json");
        List<Barter> possibleRoutes;
        try {
            possibleRoutes = BarterJsonFileReader.readBarterRoutesFromFile(new File(barterUrl.getPath()));
        } catch (JsonParseException ex) {
            logger.error("Fatal error! Could not parse the possible barter routes JSON!", ex);
            return null;
        }
        barterModulePane.setBarters(possibleRoutes);
        return barterModulePane;
    }
}
