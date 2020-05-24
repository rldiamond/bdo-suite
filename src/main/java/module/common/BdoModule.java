package module.common;

public abstract class BdoModule {

    private String title;
    private String description;
    private String iconId;

    public BdoModule(String title, String description, String iconId) {
        this.title = title;
        this.description = description;
        this.iconId = iconId;
        initialize();
    }

    /**
     * Performs initialization of the module.
     */
    protected abstract void initialize();

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

    public String getIconId() {
        return iconId;
    }

    /**
     * The display pane of the module.
     * @return
     */
    public abstract ModulePane getModulePane();
}
