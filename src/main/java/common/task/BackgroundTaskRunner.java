package common.task;

import common.logging.AppLogger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Service to run background tasks off of the JavaFX thread.
 */
public class BackgroundTaskRunner {

    private static final BackgroundTaskRunner SINGLETON = new BackgroundTaskRunner();
    private static final BooleanProperty busyProperty = new SimpleBooleanProperty(false);

    /**
     * Get the instance of the background task runner.
     *
     * @return
     */
    public static BackgroundTaskRunner getInstance() {
        return SINGLETON;
    }

    private static final AppLogger logger = AppLogger.getLogger();
    private final ExecutorService executorService = Executors.newFixedThreadPool(25);

    /**
     * Hidden constructor.
     */
    private BackgroundTaskRunner() {
    }

    /**
     * Run the provided task.
     *
     * @param task The task to run.
     */
    public Future runTask(BackgroundTask task) {
        logger.info("Submitting new task to the background task runner.");
        Runnable runnable = encapsulate(task);
        return executorService.submit(runnable::run);
    }

    /**
     * Encapsulate the task so that loading status can be determined.
     *
     * @param task The task being encapsulated.
     * @return Encapsulated task to run,
     */
    private Runnable encapsulate(BackgroundTask task) {
        return () -> {
            busyProperty().setValue(true);
            task.run();
            busyProperty().setValue(false);
        };
    }

    /**
     * @return Busy property for the background task runner.
     */
    public BooleanProperty busyProperty() {
        return busyProperty;
    }

}
