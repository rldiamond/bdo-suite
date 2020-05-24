package common.task;

public class GenericTask extends BackgroundTask {

    private final Runnable runnable;

    public GenericTask(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void doTask() {
        this.runnable.run();
    }
}
