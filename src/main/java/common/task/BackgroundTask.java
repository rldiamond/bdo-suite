package common.task;

public abstract class BackgroundTask {

    public BackgroundTask() {

    }

    /**
     * The task to run.
     */
    public void run() {
        BackgroundTaskRunner.getInstance().busyProperty().setValue(true);
        doTask();
        BackgroundTaskRunner.getInstance().busyProperty().setValue(false);
    }

    abstract void doTask();

}
