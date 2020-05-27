package module.barter.model;

import common.application.ModuleRegistration;
import common.settings.Settings;
import common.utilities.FileUtil;

public class BarterSettings implements Settings {

    private static BarterSettings SINGLETON;
    public static BarterSettings getSettings() {
        if (SINGLETON == null) {
            SINGLETON = FileUtil.loadSettings(BarterSettings.class, ModuleRegistration.BARTER);
        }

        return SINGLETON;
    }

    private boolean autofillAcceptGood;

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
