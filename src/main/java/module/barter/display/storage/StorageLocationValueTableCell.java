package module.barter.display.storage;

import common.jfx.FXUtil;
import common.jfx.components.BackgroundLoadDataGraphic;
import common.utilities.TextUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import module.barter.model.BarterGood;
import module.barter.model.BarterLevel;
import module.barter.model.StorageLocation;
import module.barter.storage.StorageItem;

public class StorageLocationValueTableCell <T extends Object, C extends StorageLocation> extends TableCell<T, C> {

    public StorageLocationValueTableCell() {

    }

    @Override
    protected void updateItem(C item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            StringProperty textProperty = new SimpleStringProperty();
            setGraphic(new BackgroundLoadDataGraphic(textProperty, () -> {

                double value = 0;
                for (StorageItem storedItem : item.getStorage().getStoredItems()) {
                    BarterGood good = BarterGood.getBarterGoodByName(storedItem.getName()).get();
                    BarterLevel level = BarterLevel.getBarterLevelByType(good.getLevel());
                    value = value + (storedItem.getAmount() * level.getValue());
                }
                final double displayValue = value;
                FXUtil.runOnFXThread(() -> textProperty.setValue(TextUtil.formatAsSilver(displayValue)));

            }));
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }

}
