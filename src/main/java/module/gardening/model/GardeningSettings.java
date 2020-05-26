package module.gardening.model;

import common.application.ModuleRegistration;
import common.settings.SaveSettingsTask;
import common.settings.Settings;
import common.task.BackgroundTaskRunner;
import common.utilities.FileUtil;

public class GardeningSettings implements Settings {

    private static GardeningSettings SINGLETON;
    public static GardeningSettings getSettings() {
        if (SINGLETON == null) {
            SINGLETON = FileUtil.loadSettings(GardeningSettings.class, ModuleRegistration.GARDENING);
        }
        // set defaults..
        boolean isDirty = false;
        if (SINGLETON.getPlayerFence() == null) {
            SINGLETON.setPlayerFence(Fence.getFences().get(0));
            isDirty = true;
        }
        if (SINGLETON.getNumberOfFences() < 1) {
            SINGLETON.setNumberOfFences(10);
            isDirty = true;
        }

        if (isDirty) {
            BackgroundTaskRunner.getInstance().runTask(new SaveSettingsTask(SINGLETON));
        }
        return SINGLETON;
    }

    private Fence playerFence;
    private int numberOfFences;

    public Fence getPlayerFence() {
        return playerFence;
    }

    public void setPlayerFence(Fence playerFence) {
        this.playerFence = playerFence;
    }

    public int getNumberOfFences() {
        return numberOfFences;
    }

    public void setNumberOfFences(int numberOfFences) {
        this.numberOfFences = numberOfFences;
    }

    @Override
    public ModuleRegistration getModule() {
        return ModuleRegistration.GARDENING;
    }
}
