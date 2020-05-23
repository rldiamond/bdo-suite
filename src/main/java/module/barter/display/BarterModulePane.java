package module.barter.display;

import javafx.scene.control.TextArea;
import module.common.ModulePane;

/**
 * The display pane for the Barter module.
 */
public class BarterModulePane extends ModulePane {

    private TextArea console;

    /**
     * Default constructor.
     */
    public BarterModulePane() {
        //Extremely basic for now to get application running.
        console = new TextArea();
        super.getChildren().add(console);
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
