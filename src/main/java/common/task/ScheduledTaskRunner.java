package common.task;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Timer;
import java.util.TimerTask;

public class ScheduledTaskRunner {

    private static final ScheduledTaskRunner SINGLETON = new ScheduledTaskRunner();
    private static final BooleanProperty busyProperty = new SimpleBooleanProperty(false);

    public static ScheduledTaskRunner getInstance() {
        return SINGLETON;
    }

    public void scheduleTask(BackgroundTask task, long minutes) {
        new Timer().scheduleAtFixedRate(encapsulate(task), 30, minutes);
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
