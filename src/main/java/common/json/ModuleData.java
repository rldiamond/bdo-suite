package common.json;

import common.application.ModuleRegistration;

/**
 * Abstract class to handle converting and saving Module data to the user's directory.
 */
public interface ModuleData {

    /**
     * The ModuleRegistration is utilized to determine the file structure for saving and reading data.
     *
     * @return
     */
    public ModuleRegistration getModule();

    /**
     * The file name is used to determine the name of the file.
     *
     * @return
     */
    public String fileName();

}
