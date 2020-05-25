package common.jfx.components;

import common.utilities.TextUtil;
import javafx.scene.control.TableCell;

public class SilverTableCell <T extends Object, C extends Number> extends TableCell<T, C> {

    public SilverTableCell() {

    }

    @Override
    protected void updateItem(C item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(TextUtil.formatAsSilver(item));
        }
    }

}
