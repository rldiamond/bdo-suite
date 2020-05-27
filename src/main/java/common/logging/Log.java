package common.logging;

import java.util.Date;

public class Log {

    private Date time;
    private String message;
    private String stacktrace;
    private AppLogger.LoggingLevel level;

    public Log() {
    }

    public Log( String message, AppLogger.LoggingLevel level, Throwable throwable) {
        this.time = new Date();
        this.message = message;
        this.level = level;
        this.stacktrace = throwable.getMessage();
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public AppLogger.LoggingLevel getLevel() {
        return level;
    }

    public void setLevel(AppLogger.LoggingLevel level) {
        this.level = level;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
