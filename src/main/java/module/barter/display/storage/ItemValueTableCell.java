package module.barter.display.storage;

import common.jfx.components.ItemWithGraphic;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import module.barter.storage.StorageItem;

public class ItemValueTableCell <T extends Object, C extends StorageItem> extends TableCell<T, C> {

    public ItemValueTableCell() {

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
