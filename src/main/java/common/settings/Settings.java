package common.settings;

import common.application.ModuleRegistration;

/**
 * Interface defining settings for a module.
 */
public interface Settings {

    /**
     * The module these settings are for.
     * @return
     */
    public ModuleRegistration getModule();

}
