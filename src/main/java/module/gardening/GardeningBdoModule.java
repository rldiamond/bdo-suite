package module.gardening;

import module.common.BdoModule;
import module.common.ModuleTool;
import module.gardening.display.GardenProfitabilityToolView;

public class GardeningBdoModule extends BdoModule {

    @Override
    protected void initialize() {
        ModuleTool gardeningProfitTool = new ModuleTool();
        gardeningProfitTool.setTitle("Profitability");
        gardeningProfitTool.setDescription("Determine potential profit for each crop.");
        gardeningProfitTool.setIconId("values");
        gardeningProfitTool.setToolView(new GardenProfitabilityToolView());
        getModuleToolbar().addTools(gardeningProfitTool);
    }
}
