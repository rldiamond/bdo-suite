package module.barter.display.storage;

import common.jfx.components.EditableTextFieldTableCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import module.barter.model.BarterModuleEvent;
import module.barter.storage.StorageItem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BarterStorageManagerTable extends TableView<StorageItem> {

    public BarterStorageManagerTable() {
        setPlaceholder(new Label("There are currently no items in this storage."));

        setEditable(true);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(buildColumns());

        Comparator<StorageItem> compareItems = Comparator.comparing(StorageItem::BarterGoodRarity).thenComparing(StorageItem::getName);

        // custom sorting
        sortPolicyProperty().set( new Callback<TableView<StorageItem>, Boolean>() {
            @Override
            public Boolean call(TableView<StorageItem> param) {
                Comparator<StorageItem> comparator = compareItems;
                FXCollections.sort(getItems(), comparator);
                return true;
            }
        });

    }

    private List<TableColumn<StorageItem, ?>> buildColumns() {
        TableColumn<StorageItem, StorageItem> itemCol = new TableColumn<>("Item");
        itemCol.setCellFactory(c -> new BarterStorageItemTableCell<>());
        itemCol.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));
        itemCol.setEditable(false);

        TableColumn<StorageItem, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getAmount())));
        amountCol.setEditable(true);
        amountCol.setCellFactory(c -> new EditableTextFieldTableCell<>());
        amountCol.setOnEditCommit(edit -> {
            String newContent = edit.getNewValue().trim();
            try {
                edit.getRowValue().setAmount(Integer.parseInt(newContent));
                fireEvent(new BarterModuleEvent(BarterModuleEvent.ModuleEventType.SAVE));
            } catch (Exception ex) {
                // invalid format
            }
            refresh();
        });

        return Arrays.asList(itemCol, amountCol);
    }

}
