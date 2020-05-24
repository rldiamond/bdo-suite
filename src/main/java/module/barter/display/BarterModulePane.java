package module.barter.display;

import common.task.BackgroundTaskRunner;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import module.barter.model.BarterRoute;
import module.barter.task.BarterOptimizationTask;
import module.common.ModulePane;

import java.util.List;

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

        // bind controls
        controlsPane.getResetButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                //TODO: Show a warning dialog
                inputPane.reset();
            }
        });

        controlsPane.getOptimizeButton().setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                doOptimize();
            }
        });
    }

    /**
     * Run the optimization process.
     */
    private void doOptimize() {
        // Get barter routes from the table
        final List<BarterRoute> barterRoutes = inputPane.getEnteredRoutes();
        // Create the background task
        BarterOptimizationTask task = new BarterOptimizationTask(barterRoutes, console);
        BackgroundTaskRunner.getInstance().runTask(task);
    }

    /**
     * Sets the contents of the console to the provided text String.
     *
     * @param text The text to display in the console.
     */
    public void setConsoleText(String text) {
        console.setText(text);
    }

    public void setBarters(List<BarterRoute> barters) {
        barters.forEach(inputPane::addRoutes);
    }

}
