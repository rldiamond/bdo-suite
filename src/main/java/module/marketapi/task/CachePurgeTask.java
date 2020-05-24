package module.marketapi.task;

import common.task.BackgroundTask;
import module.marketapi.MarketCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CachePurgeTask extends BackgroundTask {

    private static final Logger logger = LogManager.getLogger(CachePurgeTask.class);

    public CachePurgeTask(){

    }

    @Override
    public void doTask() {
        logger.info("Purging market cache..");
        MarketCache.getInstance().purgeExpiredItems();
        logger.info("Purge complete.");
    }
}
