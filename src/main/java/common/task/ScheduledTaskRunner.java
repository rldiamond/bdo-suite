package common.task;

import java.util.Timer;

public class ScheduledTaskRunner {

    private static final ScheduledTaskRunner SINGLETON = new ScheduledTaskRunner();

    public static ScheduledTaskRunner getInstance() {
        return SINGLETON;
    }

    public void scheduleTask(BackgroundTask task, long minutes) {
        new Timer().scheduleAtFixedRate(task, 30, minutes);
    }

}
