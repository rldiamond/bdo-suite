package module.barter;

import module.barter.display.RouteOptimizationToolView;
import module.barter.model.BarterGood;
import module.barter.model.BarterLevel;
import module.common.BdoModule;
import module.common.ModuleTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * BdoModule for the bartering module.
 */
public class BarterBdoModule extends BdoModule {

    private static final Logger logger = LogManager.getLogger(BarterBdoModule.class);
    private List<BarterGood> barterGoods;
    private List<BarterLevel> barterLevels;

    public BarterBdoModule() {
        super();
    }

    /**
     * Initializes the module.
     */
    protected void initialize() {
        //init the route optimization tool
        ModuleTool routeOptimizationTool = new ModuleTool();
        routeOptimizationTool.setIconId("route");
        routeOptimizationTool.setTitle("Route");
        routeOptimizationTool.setDescription("Determine optimal barter routes.");
        routeOptimizationTool.setToolView(new RouteOptimizationToolView());
        getModuleToolbar().addTools(routeOptimizationTool);
    }
}
