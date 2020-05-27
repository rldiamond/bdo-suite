package module.barter;

import module.barter.display.BarterStorageToolView;
import module.barter.display.RouteOptimizationToolView;
import module.barter.display.ValueToolView;
import module.barter.display.storage.BarterSettingsToolView;
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

        //init the storage tool
        ModuleTool storageTool = new ModuleTool();
        storageTool.setIconId("storage");
        storageTool.setTitle("Storage");
        storageTool.setDescription("Bartering storage management assistant.");
        storageTool.setToolView(new BarterStorageToolView());
        getModuleToolbar().addTools(storageTool);

        //init the values tool
        ModuleTool valueTool = new ModuleTool();
        valueTool.setIconId("values");
        valueTool.setTitle("Values");
        valueTool.setDescription("View values of various barter items.");
        valueTool.setToolView(new ValueToolView());
        getModuleToolbar().addTools(valueTool);

        //init the settings tool
        ModuleTool settingsTool = new ModuleTool();
        settingsTool.setIconId("settingsTool");
        settingsTool.setTitle("Settings");
        settingsTool.setDescription("Configure the barter module.");
        settingsTool.setToolView(new BarterSettingsToolView());
        getModuleToolbar().addTools(settingsTool);
    }
}
