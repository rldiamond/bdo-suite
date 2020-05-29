package common.logging;


import common.application.ApplicationSettings;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import common.utilities.FileUtil;
import common.utilities.ToastUtil;
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
     *
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

    /**
     * Perform all actual logging with SLF4J.
     *
     * @param loggingLevel
     * @param message
     * @param cause
     */
    private void log(final LoggingLevel loggingLevel, final String message, final Throwable cause, final boolean save) {

        switch (loggingLevel) {
            case DEBUG:
                LOGGER.debug(message, cause);
                break;
            case INFO:
                LOGGER.info(message, cause);
                break;
            case WARN:
                LOGGER.warn(message, cause);
                break;
            case ERROR:
                LOGGER.error(message, cause);
                break;
        }

        if (save) {
            logs.add(new Log(message, loggingLevel, cause));
        }

        if (ApplicationSettings.DEBUG_MODE && (loggingLevel.equals(LoggingLevel.ERROR) || loggingLevel.equals(LoggingLevel.WARN))) {
            ToastUtil.sendErrorToast("An application error occurred: " + message);
        }
    }

    private void saveLogs() {
        BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
            FileUtil.saveLogData(logs);
        }));
    }

    public void debug(String message) {
        log(LoggingLevel.DEBUG, message, null, false);
    }

    public void debug(String message, Throwable throwable) {
        log(LoggingLevel.DEBUG, message, throwable, false);
    }

    public void info(String message) {
        log(LoggingLevel.INFO, message, null, false);
    }

    public void info(String message, Throwable throwable) {
        log(LoggingLevel.INFO, message, throwable, false);
    }

    public void infoSaved(String message, Throwable throwable) {
        log(LoggingLevel.INFO, message, throwable, true);
    }

    public void warn(String message) {
        log(LoggingLevel.WARN, message, null, true);

    }

    public void warn(String message, Throwable throwable) {
        log(LoggingLevel.WARN, message, throwable, true);
    }

    public void error(String message) {
        log(LoggingLevel.ERROR, message, null, true);
    }

    public void error(String message, Throwable throwable) {
        log(LoggingLevel.ERROR, message, throwable, true);
    }
}
