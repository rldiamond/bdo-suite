package common.settings;

import common.task.BackgroundTask;
import common.utilities.FileUtil;

public class SaveSettingsTask extends BackgroundTask {

    private final Settings settings;

    public SaveSettingsTask(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void doTask() {
        FileUtil.saveSettings(settings);
    }
}
