package module.barter.display;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import module.common.ModulePane;

/**
 * The display pane for the Barter module.
 */
public class BarterModulePane extends ModulePane {

    private TextArea console;
    private BarterRouteInputPane inputPane;
    private BarterRouteControlsPane controlsPane;

    /**
     * Default constructor.
     */
    public BarterModulePane() {
        console = new TextArea();
        inputPane = new BarterRouteInputPane();
        controlsPane = new BarterRouteControlsPane();
        VBox stackEm = new VBox(5);
        stackEm.getChildren().addAll(inputPane, console, controlsPane);
        super.getChildren().add(stackEm);
    }

    /**
     * Sets the contents of the console to the provided text String.
     *
     * @param text The text to display in the console.
     */
    public void setConsoleText(String text) {
        console.setText(text);
    }

}
