package module.barter.display;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class BarterRouteControlsPane extends StackPane {

    private JFXButton resetButton;
    private JFXButton optimizeButton;

    public BarterRouteControlsPane() {
        resetButton = new JFXButton("RESET");
        resetButton.getStyleClass().add("button-flat-red");
        optimizeButton = new JFXButton("OPTIMIZE");
        optimizeButton.getStyleClass().add("button-flat-green");

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(optimizeButton, resetButton);
        this.getChildren().addAll(buttonContainer);
        setPadding(new Insets(5,5,10,5));
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
