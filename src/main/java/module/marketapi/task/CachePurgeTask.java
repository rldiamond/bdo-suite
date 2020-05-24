package module.marketapi.task;

import common.task.BackgroundTask;
import module.marketapi.MarketCache;

public class CachePurgeTask extends BackgroundTask {

    public CachePurgeTask(){

    }

    @Override
    public void doTask() {
        MarketCache.getInstance().purgeExpiredItems();
    }
}
