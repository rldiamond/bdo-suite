package module.common;

public abstract class BdoModule {

    private ModuleToolbar moduleToolbar;

    public BdoModule() {
        moduleToolbar = new ModuleToolbar();
        initialize();
    }

    /**
     * Performs initialization of the module.
     */
    protected abstract void initialize();

    public ModuleToolbar getModuleToolbar(){
        return moduleToolbar;
    }
}
