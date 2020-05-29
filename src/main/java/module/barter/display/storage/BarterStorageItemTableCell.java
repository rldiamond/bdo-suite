package module.barter.display.storage;

import common.jfx.components.ItemWithGraphic;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import module.barter.model.BarterGood;
import module.barter.storage.StorageItem;

import java.util.Optional;

public class BarterStorageItemTableCell<T extends Object, C extends StorageItem> extends TableCell<T, C> {

    public BarterStorageItemTableCell() {

    }

    @Override
    protected void updateItem(C item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            ItemWithGraphic graphic =new ItemWithGraphic(item);
            setGraphic(graphic);
            graphic.setBold(true);
            getColorFromGoodLevel(item).ifPresent(graphic::setColor);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

    private Optional<Color> getColorFromGoodLevel(final C item) {
        final BarterGood good = BarterGood.getBarterGoodByName(item.getName()).orElse(null);
        Color color = null;
        if (good != null) {
            switch (good.getLevelType()) {
                case TWO:
                    color = Color.rgb(138,239,122);
                    break;
                case THREE:
                    color = Color.rgb(62,144,191);
                    break;
                case FOUR:
                    color = Color.rgb(238,195,82);
                    break;
                case FIVE:
                    color = Color.rgb(197,105,39);
                    break;
                default:
                    break;
            }
        }
        return Optional.ofNullable(color);
    }
}
