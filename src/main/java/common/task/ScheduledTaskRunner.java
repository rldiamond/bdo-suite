package common.task;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskRunner {

    private static final ScheduledTaskRunner SINGLETON = new ScheduledTaskRunner();
    private static final BooleanProperty busyProperty = new SimpleBooleanProperty(false);

    public static ScheduledTaskRunner getInstance() {
        return SINGLETON;
    }

    public void scheduleTask(BackgroundTask task, long minutes) {
        scheduleTask(task, minutes, 1);
    }

    public void scheduleTask(BackgroundTask task, long repeatMinutes, long delaySeconds) {
        new Timer().scheduleAtFixedRate(encapsulate(task), TimeUnit.SECONDS.toMillis(delaySeconds), TimeUnit.MINUTES.toMillis(repeatMinutes));
    }

    private TimerTask encapsulate(BackgroundTask task) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                busyProperty.setValue(true);
                task.run();
                busyProperty.setValue(false);
            }
        };
        return timerTask;
    }

    public BooleanProperty busyProperty() {
        return busyProperty;
    }
}
