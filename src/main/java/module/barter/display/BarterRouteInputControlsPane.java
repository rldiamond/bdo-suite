package module.barter.display;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class BarterRouteInputControlsPane extends StackPane {

    private Button addBarterButton;
    private Button removeBarterButton;

    public BarterRouteInputControlsPane() {
        addBarterButton = new Button("Add Barter");
        removeBarterButton = new Button("Remove Barter");

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
