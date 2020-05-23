package module.barter;

import module.barter.display.BarterModulePane;
import module.common.BdoModule;
import module.common.ModulePane;

/**
 * BdoModule for the bartering module.
 */
public class BarterBdoModule extends BdoModule {


    public BarterBdoModule() {
        super("Bartering", "Calculate optimal routes for your bartering session.");
    }

    @Override
    public ModulePane getModulePane() {
        return new BarterModulePane();
    }
}
