package common.application;

import module.barter.BarterBdoModule;
import module.common.BdoModule;
import module.gardening.GardeningBdoModule;
import module.marketapi.MarketApiBdoModule;

public enum ModuleRegistration {
    BARTER(BarterBdoModule.class, "Bartering", "Calculate optimal routes for your bartering session.", "barter"),
    GARDENING(GardeningBdoModule.class, "Gardening", "Determine the most profitable crops to garden.", "garden"),
    MARKET(MarketApiBdoModule.class, "Market", "Various tools utilizing the Central Market.", "market");

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
