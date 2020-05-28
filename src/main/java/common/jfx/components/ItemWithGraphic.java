package common.jfx.components;

import common.jfx.FXUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import module.common.model.BdoItem;

public class ItemWithGraphic extends StackPane {

    private ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>();
    private BooleanProperty boldProperty = new SimpleBooleanProperty(false);

    public ItemWithGraphic(BdoItem item) {
        this(item, null);
    }

    public ItemWithGraphic(BdoItem item, Color color) {
        HBox wrapper = new HBox(10);
        wrapper.setAlignment(Pos.CENTER_LEFT);

        ItemImage itemImage = new ItemImage(item.getItemId());
        Label itemNameLabel = new Label(item.getName());

        wrapper.getChildren().setAll(itemImage, itemNameLabel);
        getChildren().setAll(wrapper);

        colorProperty.addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                refresh(item, itemNameLabel);
            }
        }));

        boldProperty.addListener((observable -> refresh(item, itemNameLabel)));

        setColor(color);
    }

    private void refresh(BdoItem item, Label label) {
        String style = "";
        if (boldProperty.get()) {
            style = "-fx-font-weight: bold;";
        }

        if (colorProperty.get() != null) {
            style += "-fx-text-fill: " + FXUtil.toHexCode(colorProperty.get()) + ";";
        }

        label.setStyle(style);
    }

    public void setColor(Color color) {
        colorProperty.setValue(color);
    }

    public void setBold(boolean boldProperty) {
        this.boldProperty.set(boldProperty);
    }
}
