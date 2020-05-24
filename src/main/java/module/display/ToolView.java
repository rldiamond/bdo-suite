package module.display;

import common.jfx.components.Card;
import javafx.scene.Node;

public abstract class ToolView extends Card {

    public ToolView(String title) {
        super(title);
        setDisplayedContent(getDisplay());
    }

    public abstract Node getDisplay();

}
