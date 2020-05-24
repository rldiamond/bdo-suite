package module.common;

import common.jfx.components.Card;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

public class ModulePane extends StackPane {

    private Card card;

    public ModulePane(String title) {
        setPadding(new Insets(20,20,20,20));

        card = new Card(title);
        getChildren().setAll(card);
    }

    public Card getCard() {
        return card;
    }
}
