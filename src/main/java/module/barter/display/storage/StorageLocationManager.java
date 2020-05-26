package module.barter.display.storage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import common.jfx.FXUtil;
import common.jfx.components.Card;
import common.task.DisplayTaskRunner;
import common.task.GenericTask;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import module.barter.model.BarterModuleEvent;
import module.barter.model.StorageLocation;

public class StorageLocationManager extends StackPane {

    private final BooleanProperty loading = new SimpleBooleanProperty(true);
    private final StorageLocation storageLocation;

    public StorageLocationManager(StorageLocation storageLocation) {
        this.storageLocation = storageLocation;
        //immediately display a loading spinner
        JFXSpinner loadingSpinner = new JFXSpinner();
        loadingSpinner.setPrefSize(25,25);
        loadingSpinner.visibleProperty().bind(loading);
        loadingSpinner.managedProperty().bind(loading);
        setAlignment(Pos.CENTER);
        getChildren().setAll(loadingSpinner);

        DisplayTaskRunner.getInstance().runTask(new GenericTask(this::initialize));

    }

    public StorageLocation getStorageLocation() {
        return storageLocation;
    }

    private void initialize() {

        Card card = new Card(storageLocation.getName());
        card.setMaxHeight(350);
        BarterStorageManagerTable table = new BarterStorageManagerTable();
        table.setItems(FXCollections.observableArrayList(storageLocation.getStorage().getStoredItems()));
        card.setDisplayedContent(table);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        JFXButton addButton = new JFXButton("ADD");
        addButton.getStyleClass().add("button-flat-gray");
        addButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                DisplayTaskRunner.getInstance().runTask(new GenericTask(() -> {
                    BarterStorageAddItemCard addItemCard = new BarterStorageAddItemCard(this, storageLocation);
                    FXUtil.runOnFXThread(addItemCard::show);
                }));
            }
        });

        JFXButton deleteButton = new JFXButton("DELETE");
        deleteButton.getStyleClass().add("button-flat-red");
        deleteButton.disableProperty().bind(Bindings.isNull(table.getSelectionModel().selectedItemProperty()));
        deleteButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                storageLocation.getStorage().getStoredItems().remove(table.getSelectionModel().getSelectedItem());
                table.refresh();
                fireEvent(new BarterModuleEvent(BarterModuleEvent.ModuleEventType.SAVE));
            }
        });

        this.addEventHandler(BarterModuleEvent.BARTERSAVEEVENT, e -> {
            table.refresh();
        });
        buttonContainer.getChildren().setAll(addButton, deleteButton);
        card.setFooterContent(buttonContainer);

        try {
            //TimeUnit.SECONDS.sleep(5);
            FXUtil.runOnFXThread(() -> {
                getChildren().add(card);
                loading.set(false);
            });
        } catch (Exception ex) {

        }
    }

}
