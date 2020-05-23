package module.common;

public abstract class BdoModule {

    private String title;
    private String description;

    public BdoModule(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public abstract ModulePane getModulePane();
}
