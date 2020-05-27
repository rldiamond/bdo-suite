package common.settings;

import common.task.BackgroundTask;
import common.utilities.FileUtil;

/**
 * Task for saving settings.
 */
public class SaveSettingsTask extends BackgroundTask {

    private final Settings settings;

    /**
     * Construct the save settings task for the provided settings.
     *
     * @param settings The settings to save.
     */
    public SaveSettingsTask(Settings settings) {
        this.settings = settings;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void doTask() {
        FileUtil.saveSettings(settings);
    }
}
