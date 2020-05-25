package common.task;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Service to run background tasks off of the JavaFX thread.
 */
public class BackgroundTaskRunner {

    private static final BackgroundTaskRunner SINGLETON = new BackgroundTaskRunner();
    private static final BooleanProperty busyProperty = new SimpleBooleanProperty(false);

    /**
     * Get the instance of the background task runner.
     * @return
     */
    public static final BackgroundTaskRunner getInstance() {
        return SINGLETON;
    }

    private static final Logger logger = LogManager.getLogger(BackgroundTaskRunner.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);

    private BackgroundTaskRunner() {
    }

    /**
     * Run the provided task.
     * @param task The task to run.
     */
    public Future runTask(BackgroundTask task) {
        logger.info("Submitting new task to the background task runner.");
        Runnable runnable = encapsulate(task);
        return executorService.submit(runnable::run);
    }

    private Runnable encapsulate(BackgroundTask task) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                busyProperty().setValue(true);
                task.run();
                busyProperty().setValue(false);
            }
        };
        return runnable;
    }

    public boolean isRunning() {
        return ((ThreadPoolExecutor) executorService).getActiveCount() > 0;
    }

    public BooleanProperty busyProperty() {
        return busyProperty;
    }

}
