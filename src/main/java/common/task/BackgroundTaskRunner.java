package common.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service to run background tasks off of the JavaFX thread.
 */
public class BackgroundTaskRunner {

    private static final BackgroundTaskRunner SINGLETON = new BackgroundTaskRunner();

    /**
     * Get the instance of the background task runner.
     * @return
     */
    private static final BackgroundTaskRunner getInstance() {
        return SINGLETON;
    }

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private BackgroundTaskRunner() {

    }

    /**
     * Run the provided task.
     * @param task The task to run.
     */
    public void runTask(BackgroundTask task) {
        executorService.submit(task::run);
    }

}
