package module.barter.display;

import common.json.JsonFileReader;
import common.json.JsonParseException;
import javafx.scene.control.TextArea;
import module.barter.BarterOptimizer;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;
import module.common.ModulePane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * The display pane for the Barter module.
 */
public class BarterModulePane extends ModulePane {

    private static final Logger logger = LogManager.getLogger(BarterModulePane.class);

    /**
     * Default constructor.
     */
    public BarterModulePane() {
        //Extremely basic for now to get application running.
        TextArea outputTextArea = new TextArea();
        List<BarterRoute> possibleRoutes = null;
        try {
            URL url = BarterModulePane.class.getClassLoader().getResource("barter.json");
            possibleRoutes = JsonFileReader.readListFromFile(new File(url.getPath()));
        } catch (JsonParseException ex) {
            logger.error("Could not parse JSON file!", ex);
            return;
        }
        BarterPlan plan = BarterOptimizer.optimize(possibleRoutes);
        outputTextArea.setText(plan.toString());
        super.getChildren().add(outputTextArea);
    }

}
