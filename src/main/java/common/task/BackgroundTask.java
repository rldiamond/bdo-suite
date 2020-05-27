package common.task;

import common.logging.AppLogger;

import java.util.TimerTask;

/**
 * Abstract class defining tasks that can be ran in the background.
 */
public abstract class BackgroundTask extends TimerTask {

    private static final AppLogger logger = AppLogger.getLogger();

    /**
     * Default constructor.
     */
    public BackgroundTask() {
        //..
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

    /**
     * The contents of this method are ran when the executor runs this background task.
     */
    abstract public void doTask();

}
