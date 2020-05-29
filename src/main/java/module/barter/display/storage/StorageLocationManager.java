package module.barter.display.storage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import common.jfx.FXUtil;
import common.jfx.components.Card;
import common.task.BackgroundTaskRunner;
import common.task.DisplayTaskRunner;
import common.task.GenericTask;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import module.barter.model.BarterGood;
import module.barter.model.BarterLevel;
import module.barter.model.BarterModuleEvent;
import module.barter.model.StorageLocation;
import module.barter.storage.StorageItem;

public class StorageLocationManager extends StackPane {

    private final BooleanProperty loading = new SimpleBooleanProperty(true);
    private final StorageLocation storageLocation;
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty slotsTextProperty = new SimpleStringProperty();
    private BarterStorageManagerTable table;

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

    public void refresh() {
        titleProperty.setValue(storageLocation.getName());
        table.setItems(FXCollections.observableArrayList(storageLocation.getStorage().getStoredItems()));
        table.refresh();
        table.sort();
        BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
            int slotsUsed = calculateSlotsUsed();
            FXUtil.runOnFXThread(() -> slotsTextProperty.setValue(slotsUsed + "/" +storageLocation.getStorage().getCapacity()));
        }));
    }

    private int calculateSlotsUsed() {
        int slots = 0;
        for (StorageItem storedItem : storageLocation.getStorage().getStoredItems()) {
            BarterGood good = BarterGood.getBarterGoodByName(storedItem.getName()).get();

            if (BarterLevel.getBarterLevelByType(good.getLevel()).doesStack()) {
                slots++;
            } else {
                slots += storedItem.getAmount();
            }
        }

        return slots;
    }

    private void initialize() {

        Card card = new Card(titleProperty);
        card.setMaxHeight(350);
        table = new BarterStorageManagerTable();
        card.setDisplayedContent(new StackPane(table));

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

        AnchorPane slotsPane = new AnchorPane();
        Label slotsLabel = new Label();
        slotsLabel.setStyle("-fx-font-size: 14px");
        AnchorPane.setRightAnchor(slotsLabel, 5D);
        AnchorPane.setBottomAnchor(slotsLabel, 5D);
        slotsLabel.setAlignment(Pos.CENTER_RIGHT);
        slotsLabel.textProperty().bind(slotsTextProperty);
        slotsPane.getChildren().setAll(slotsLabel);
        card.setFooterContent(new StackPane(slotsPane, buttonContainer));

        FXUtil.runOnFXThread(() -> {
            refresh();
            getChildren().add(card);
            loading.set(false);
        });
    }

}
