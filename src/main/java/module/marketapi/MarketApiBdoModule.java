package module.marketapi;

import common.settings.UserSettings;
import common.task.ScheduledTaskRunner;
import module.common.BdoModule;
import module.marketapi.task.CachePurgeTask;

public class MarketApiBdoModule extends BdoModule {

    @Override
    protected void initialize() {
        // initialize market cache purging event
        ScheduledTaskRunner.getInstance().scheduleTask(new CachePurgeTask(), UserSettings.getInstance().getMarketCachePurgeRepeatTimeMinutes());
    }
}
