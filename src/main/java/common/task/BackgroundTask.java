package common.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TimerTask;

public abstract class BackgroundTask extends TimerTask {
    private static final Logger logger = LogManager.getLogger(BackgroundTask.class);

    public BackgroundTask() {

    }

    /**
     * The task to run.
     */
    public void run() {
        try {
            doTask();
        } catch (Exception ex) {
            logger.error("An unexpected error occurred when executing a background task!", ex);
        }
    }

    abstract public void doTask();

}
