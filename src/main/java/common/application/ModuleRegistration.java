package common.application;

import module.barter.BarterBdoModule;
import module.common.BdoModule;

public enum ModuleRegistration {
    BARTER(BarterBdoModule.class, "Bartering", "Calculate optimal routes for your bartering session.", "barter");

    private final Class<? extends BdoModule> module;
    private final String title;
    private final String description;
    private final String iconId;

    private ModuleRegistration(Class<? extends BdoModule> module, String title, String description, String iconId) {
        this.module = module;
        this.title = title;
        this.description = description;
        this.iconId = iconId;
    }

    public Class<? extends BdoModule> getModuleClass() {
        return module;
    }

    public BdoModule getNewModule() {
        try {
            return getModuleClass().getConstructor().newInstance();
        } catch (Exception ex) {
            // shouldnt occur.
            return null;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIconId() {
        return iconId;
    }
}
