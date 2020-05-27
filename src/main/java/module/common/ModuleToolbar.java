package module.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleToolbar {

    private List<ModuleTool> tools = new ArrayList<>();

    public ModuleToolbar() {

    }

    public List<ModuleTool> getTools() {
        return tools;
    }

    public void addTools(ModuleTool... tools) {
        Collections.addAll(this.tools, tools);
    }
}
