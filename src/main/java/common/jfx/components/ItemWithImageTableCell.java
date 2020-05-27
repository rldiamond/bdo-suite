package common.jfx.components;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import module.common.model.BdoItem;

public class ItemWithImageTableCell <T extends Object, C extends BdoItem> extends TableCell<T, C> {

    public ItemWithImageTableCell() {

    }

    @Override
    protected void updateItem(C item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setGraphic(new ItemWithGraphic(item));
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
