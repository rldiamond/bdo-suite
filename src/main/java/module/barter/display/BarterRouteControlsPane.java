package module.barter.display;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class BarterRouteControlsPane extends StackPane {

    private Button resetButton;
    private Button optimizeButton;

    public BarterRouteControlsPane() {
        resetButton = new Button("Reset");
        optimizeButton = new Button("Optimize");
        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(resetButton,optimizeButton);
        this.getChildren().addAll(buttonContainer);
    }

    /**
     * The button that resets the input data.
     * @return
     */
    public Button getResetButton() {
        return resetButton;
    }

    /**
     * The button that begins optimization of the barter route.
     * @return
     */
    public Button getOptimizeButton() {
        return optimizeButton;
    }
}
