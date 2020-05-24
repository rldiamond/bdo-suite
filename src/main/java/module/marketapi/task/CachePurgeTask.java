package module.marketapi.task;

import common.task.BackgroundTask;
import module.marketapi.MarketCache;

public class CachePurgeTask implements BackgroundTask {

    public CachePurgeTask(){

    }

    @Override
    public void run() {
        MarketCache.getInstance().purgeExpiredItems();
    }
}
