package common.logging;


import common.application.ApplicationSettings;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import common.utilities.FileUtil;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;

/**
 * Custom application logger that is capable of saving simple JSON logs to the user directory for access.
 */
public class AppLogger {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();
    private static AppLogger APPLICATION_LOGGER = new AppLogger();

    /**
     * Get the instance of the logger.
     * @return
     */
    public static AppLogger getLogger() {
        return APPLICATION_LOGGER;
    }

    private final ObservableList<Log> logs = FXCollections.observableArrayList();

    enum LoggingLevel {
        DEBUG, INFO, WARN, ERROR
    }

    /**
     * Hidden constructor.
     */
    private AppLogger() {
        // Load the logs
        logs.addAll(FileUtil.loadLogs());

        logs.addListener((ListChangeListener.Change<? extends Log> c) -> {
            c.next();
            if (ApplicationSettings.WRITE_LOG_FILE) {
                saveLogs();
            }
        });
    }

    private void saveLogs() {
        BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
            FileUtil.saveLogData(logs);
        }));
    }

    public void debug(String message) {
        debug(message, null);
    }

    public void debug(String message, Throwable throwable) {
        LOGGER.debug(message, throwable);
    }

    public void info(String message) {
        info(message, null);
    }

    public void info(String message, Throwable throwable) {
        LOGGER.info(message, throwable);
    }

    public void infoSaved(String message, Throwable throwable) {
        info(message, throwable);
        logs.add(new Log( message, LoggingLevel.INFO, throwable));
    }

    public void warn(String message) {
        warn(message, null);
    }

    public void warn(String message, Throwable throwable) {
        LOGGER.warn(message, throwable);
        logs.add(new Log(message, LoggingLevel.WARN, throwable));
    }

    public void error(String message) {
        error(message, null);
    }

    public void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
        logs.add(new Log(message, LoggingLevel.ERROR, throwable));
    }
}
