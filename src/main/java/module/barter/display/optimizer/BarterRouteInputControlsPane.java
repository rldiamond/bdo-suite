package module.barter.display.optimizer;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class BarterRouteInputControlsPane extends StackPane {

    private JFXButton addBarterButton;
    private JFXButton removeBarterButton;

    public BarterRouteInputControlsPane() {
        addBarterButton = new JFXButton("ADD");
        addBarterButton.getStyleClass().add("button-flat-gray");
        removeBarterButton = new JFXButton("REMOVE");
        removeBarterButton.getStyleClass().add("button-flat-gray");

        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.getChildren().addAll(addBarterButton, removeBarterButton);
        this.getChildren().add(buttonBar);
        setPadding(new Insets(10,5,5,5));

    }

    public Button getAddBarterButton() {
        return addBarterButton;
    }

    public Button getRemoveBarterButton() {
        return removeBarterButton;
    }
}
