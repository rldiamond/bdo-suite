package common.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BackgroundTask {
    private static final Logger logger = LogManager.getLogger(BackgroundTask.class);

    public BackgroundTask() {

    }

    /**
     * The task to run.
     */
    public void run() {
        BackgroundTaskRunner.getInstance().busyProperty().setValue(true);
        try {
            doTask();
        } catch (Exception ex) {
            logger.error("An unexpected error occurred when executing a background task!", ex);
        } finally {
            BackgroundTaskRunner.getInstance().busyProperty().setValue(false);
        }
    }

    abstract public void doTask();

}
