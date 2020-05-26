package module.barter.display.storage;

import common.jfx.components.ItemWithImageTableCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import module.barter.storage.StorageItem;

import java.util.Arrays;
import java.util.List;

public class BarterStorageManagerTable extends TableView<StorageItem> {

    public BarterStorageManagerTable() {
        setPlaceholder(new Label("There are currently no items in this storage."));

        setEditable(false);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(buildColumns());

    }

    private List<TableColumn<StorageItem, ?>> buildColumns() {
        TableColumn<StorageItem, StorageItem> itemCol = new TableColumn<>("Item");
        itemCol.setCellFactory(c -> new ItemWithImageTableCell<>());
        itemCol.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));

        TableColumn<StorageItem, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getAmount())));

        return Arrays.asList(itemCol, amountCol);
    }

}
