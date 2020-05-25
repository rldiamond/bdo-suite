package common.jfx.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import module.common.model.BdoItem;

public class ItemWithGraphic extends StackPane {

    public ItemWithGraphic(BdoItem item) {
        HBox wrapper = new HBox(10);
        wrapper.setAlignment(Pos.CENTER_LEFT);

        ItemImage itemImage = new ItemImage(item.getItemId());
        Label itemNameLabel = new Label(item.getName());

        wrapper.getChildren().setAll(itemImage, itemNameLabel);
        getChildren().setAll(wrapper);

    }

}
