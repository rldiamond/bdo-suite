package common.json;

import common.application.ModuleRegistration;

/**
 * Abstract class to handle converting objects to JSON.
 */
public interface ModuleData {

    public ModuleRegistration getModule();

    public String fileName();

}
