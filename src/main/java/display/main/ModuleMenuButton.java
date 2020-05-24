package display.main;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import module.common.BdoModule;

//TODO: Style, animations, tooltip, fire event to trigger change of modules.
public class ModuleMenuButton extends StackPane {

    public ModuleMenuButton(BdoModule module) {
        Label title = new Label(module.getTitle());
        getChildren().add(title);
    }
}
