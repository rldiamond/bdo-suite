package module.common;

import module.display.ToolView;

public class ModuleTool {

    private String iconId;
    private String title;
    private String description;
    private ToolView toolView;


    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ToolView getToolView() {
        return toolView;
    }

    public void setToolView(ToolView toolView) {
        this.toolView = toolView;
    }
}
