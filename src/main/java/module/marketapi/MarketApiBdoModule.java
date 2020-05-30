package module.marketapi;

import common.settings.UserSettings;
import common.task.ScheduledTaskRunner;
import module.common.BdoModule;
import module.common.ModuleTool;
import module.marketapi.display.MarketDisplayToolView;
import module.marketapi.task.CachePurgeTask;

public class MarketApiBdoModule extends BdoModule {

    @Override
    protected void initialize() {

        ModuleTool marketDisplayTool = new ModuleTool();
        marketDisplayTool.setIconId("market");
        marketDisplayTool.setTitle("Market");
        marketDisplayTool.setDescription("Various tools for searching and calculating market prices.");
        marketDisplayTool.setToolView(new MarketDisplayToolView());
        getModuleToolbar().addTools(marketDisplayTool);


        // initialize market cache purging event
        ScheduledTaskRunner.getInstance().scheduleTask(new CachePurgeTask(), UserSettings.getInstance().getMarketCachePurgeRepeatTimeMinutes());
    }
}
