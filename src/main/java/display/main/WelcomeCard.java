package display.main;

import common.jfx.components.Card;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class WelcomeCard extends StackPane {

    public WelcomeCard() {
        Card card = new Card();

        Label label = new Label("Select a module on the left to begin.");

        card.setDisplayedContent(label);
        getChildren().setAll(card);
    }
}
