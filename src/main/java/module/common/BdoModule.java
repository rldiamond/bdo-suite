package module.common;

import sun.security.pkcs11.Secmod;

public abstract class BdoModule {

    private String title;
    private String description;

    public BdoModule(String title, String description) throws ModuleException {
        this.title = title;
        this.description = description;
        initialize();
    }

    /**
     * Performs initialization of the module.
     */
    protected abstract void initialize() throws ModuleException;

    /**
     * The human-friendly title of the module.
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * The human-friendly description of the module.
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * The display pane of the module.
     * @return
     */
    public abstract ModulePane getModulePane();
}
