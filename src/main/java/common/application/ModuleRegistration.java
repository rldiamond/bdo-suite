package common.application;

import module.barter.BarterBdoModule;
import module.common.BdoModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages modules registered with the application.
 * This will assist in allowing the user to enable or disable modules.
 */
public class ModuleRegistration {

    private static final Logger logger = LogManager.getLogger(ModuleRegistration.class);
    private static final List<BdoModule> REGISTERED_MODULES = new ArrayList<>();

    static {
        registerModule(new BarterBdoModule());
    }

    public static void registerModule(BdoModule module) {
        REGISTERED_MODULES.add(module);
    }

    public static void unregisterModule(BdoModule module) {

    }

    public static List<BdoModule> getRegisteredModules() {
        return REGISTERED_MODULES;
    }
}
