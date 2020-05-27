package module.gardening;

import common.task.BackgroundTaskRunner;
import module.common.BdoModule;
import module.common.ModuleTool;
import module.gardening.display.GardenProfitabilityToolView;
import module.gardening.display.GardenSettingsToolView;
import module.gardening.task.GetCropValuesTask;

public class GardeningBdoModule extends BdoModule {

    @Override
    protected void initialize() {
        //immediately fetch crop values.
        BackgroundTaskRunner.getInstance().runTask(new GetCropValuesTask());

        ModuleTool gardeningProfitTool = new ModuleTool();
        gardeningProfitTool.setTitle("Profitability");
        gardeningProfitTool.setDescription("Determine potential profit for each crop.");
        gardeningProfitTool.setIconId("values");
        gardeningProfitTool.setToolView(new GardenProfitabilityToolView());
        getModuleToolbar().addTools(gardeningProfitTool);

        ModuleTool gardeningSettingsTool = new ModuleTool();
        gardeningSettingsTool.setTitle("Settings");
        gardeningSettingsTool.setDescription("Configure user settings for the Gardening module.");
        gardeningSettingsTool.setIconId("settingsTool");
        gardeningSettingsTool.setToolView(new GardenSettingsToolView());
        getModuleToolbar().addTools(gardeningSettingsTool);

    }
}
