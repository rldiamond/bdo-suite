package module.display;

import common.jfx.components.Card;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

public abstract class ToolView extends StackPane {

    private Card card;

    public ToolView(String title) {
        setPadding(new Insets(20,20,20,20));

        card = new Card(title);
        getChildren().setAll(card);
    }

    protected Card getCard() {
        return card;
    }

}
