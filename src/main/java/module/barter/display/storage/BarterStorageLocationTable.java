package module.barter.display.storage;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import module.barter.model.StorageLocation;

import java.util.Arrays;
import java.util.List;

public class BarterStorageLocationTable extends TableView<StorageLocation> {

    public BarterStorageLocationTable() {
        setPlaceholder(new Label("Add at least one storage location to manage."));

        // build the table
        setEditable(false);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        getColumns().addAll(buildColumns());
    }

    private List<TableColumn<StorageLocation, ?>> buildColumns() {
        TableColumn<StorageLocation, String> locationNameCol = new TableColumn<>("Item");
        locationNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        TableColumn<StorageLocation, String> totalSlotsCol = new TableColumn<>("Slots");
        totalSlotsCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStorage().getCapacity())));

        TableColumn<StorageLocation, StorageLocation> valueCol = new TableColumn<>("Value of Goods");
        valueCol.setCellFactory(c -> new StorageLocationValueTableCell<>());
        valueCol.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue()));

        return Arrays.asList(locationNameCol, totalSlotsCol, valueCol);
    }

}
