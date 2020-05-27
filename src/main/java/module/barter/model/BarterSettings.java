package module.barter.model;

import common.application.ModuleRegistration;
import common.settings.Settings;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import common.utilities.FileUtil;

public class BarterSettings implements Settings {

    private static BarterSettings SINGLETON;

    public static BarterSettings getSettings() {
        if (SINGLETON == null) {
            SINGLETON = FileUtil.loadSettings(BarterSettings.class, ModuleRegistration.BARTER);
        }

        //Set defaults
        boolean isDirty = false;
        if (SINGLETON.getShipWeightCapacity() == 0) {
            SINGLETON.setShipWeightCapacity(5100);
            isDirty = true;
        }

        if (isDirty) {
            BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
                FileUtil.saveSettings(SINGLETON);
            }));
        }

        return SINGLETON;
    }

    private boolean autofillAcceptGood;
    private int shipWeightCapacity;
    private int shipStorageCapacity;

    public int getShipWeightCapacity() {
        return shipWeightCapacity;
    }

    public void setShipWeightCapacity(int shipWeightCapacity) {
        this.shipWeightCapacity = shipWeightCapacity;
    }

    public int getShipStorageCapacity() {
        return shipStorageCapacity;
    }

    public void setShipStorageCapacity(int shipStorageCapacity) {
        this.shipStorageCapacity = shipStorageCapacity;
    }

    public boolean isAutofillAcceptGood() {
        return autofillAcceptGood;
    }

    public void setAutofillAcceptGood(boolean autofillAcceptGood) {
        this.autofillAcceptGood = autofillAcceptGood;
    }

    @Override
    public ModuleRegistration getModule() {
        return ModuleRegistration.BARTER;
    }
}
