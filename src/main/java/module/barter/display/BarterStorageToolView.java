package module.barter.display;

import com.jfoenix.controls.JFXButton;
import common.jfx.FXUtil;
import common.jfx.components.Card;
import common.task.BackgroundTaskRunner;
import common.task.DisplayTaskRunner;
import common.task.GenericTask;
import common.utilities.FileUtil;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import module.barter.display.storage.BarterStorageLocationEditCard;
import module.barter.display.storage.BarterStorageLocationTable;
import module.barter.display.storage.StorageLocationManager;
import module.barter.model.BarterModuleEvent;
import module.barter.model.PlayerStorageLocations;
import module.barter.model.StorageLocation;
import module.display.ToolView;

import java.util.ArrayList;
import java.util.List;

public class BarterStorageToolView extends ToolView {

    private final ObjectProperty<PlayerStorageLocations> storageLocationsProperty = new SimpleObjectProperty<>();
    private ObservableList<StorageLocation> storageLocations = FXCollections.observableArrayList();
    private final VBox cardContainer;
    private final VBox locationsContainer = new VBox(15);
    private BarterStorageLocationTable storageLocationTable = new BarterStorageLocationTable();;
    private List<StorageLocationManager> locationManagerCards = new ArrayList<>();

    public BarterStorageToolView() {
        super("Storage Location Management");

        // load the storage locations
        BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
            storageLocationsProperty.setValue(FileUtil.loadModuleData(PlayerStorageLocations.class));
            storageLocations.addAll(storageLocationsProperty.get().getStorageLocations());
        }));

        // save event handler
        this.addEventHandler(BarterModuleEvent.BARTERSAVEEVENT, e -> {
            BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
                storageLocationsProperty.getValue().setStorageLocations(storageLocations);
                FileUtil.saveModuleData(storageLocationsProperty.getValue());
                updateLocationCards();
            }));
            storageLocationTable.refresh();
            e.consume();
        });

        // delete event handler
        this.addEventHandler(BarterModuleEvent.BARTERDELETELOCATIONEVENT, e -> {
            BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
                storageLocations.remove(storageLocationTable.getSelectionModel().getSelectedItem());
                storageLocationsProperty.getValue().setStorageLocations(storageLocations);
                FileUtil.saveModuleData(storageLocationsProperty.getValue());
            }));
            storageLocationTable.refresh();
            e.consume();
        });

        // remove default as we want multiple cards here
        cardContainer = new VBox(15);
        ScrollPane scrollPane = new ScrollPane(cardContainer);
        scrollPane.getStyleClass().add("edge-to-edge");
        scrollPane.setFitToWidth(true);
        getChildren().setAll(scrollPane);

        initManagementCard();

        cardContainer.getChildren().add(locationsContainer);

        storageLocations.addListener((ListChangeListener.Change<? extends StorageLocation> c) -> {
            c.next();
            DisplayTaskRunner.getInstance().runTask(new GenericTask(() -> {
                c.getAddedSubList().forEach(this::createLocationManagementCard);
                c.getRemoved().forEach(this::removeLocationManagementCard);
            }));
        });
    }

    private void updateLocationCards() {
        locationManagerCards.clear();
        FXUtil.runOnFXThread(() -> {
            locationsContainer.getChildren().clear();
            storageLocations.stream().map(StorageLocationManager::new).forEach(locationsContainer.getChildren()::add);
        });
    }

    private void createLocationManagementCard(StorageLocation storageLocation) {
        StorageLocationManager manager = new StorageLocationManager(storageLocation);
        locationManagerCards.add(manager);
        FXUtil.runOnFXThread(() -> locationsContainer.getChildren().add(manager));

    }

    private void removeLocationManagementCard(StorageLocation storageLocation) {
        locationManagerCards.stream().filter(card -> card.getStorageLocation().equals(storageLocation)).findAny().ifPresent(card -> {
            locationManagerCards.remove(card);
            FXUtil.runOnFXThread(() -> locationsContainer.getChildren().remove(card));
        });
    }

    private void initManagementCard() {
        //Add & Edit storage section
        Card card = new Card("Location Management");
        card.setMaxHeight(USE_PREF_SIZE);
        card.setPrefHeight(250);
        card.setMinHeight(USE_PREF_SIZE);
        cardContainer.getChildren().add(card);

        HBox managementWrapper = new HBox(15);
        card.setDisplayedContent(managementWrapper);
        storageLocationTable.setItems(storageLocations);
        HBox.setHgrow(storageLocationTable, Priority.ALWAYS);
        managementWrapper.getChildren().add(storageLocationTable);

        // buttons on right
        VBox buttonsContainer = new VBox(15);
        buttonsContainer.setAlignment(Pos.CENTER);
        managementWrapper.getChildren().add(buttonsContainer);

        JFXButton addButton = new JFXButton("ADD");
        addButton.getStyleClass().add("button-flat-gray");
        addButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                storageLocations.add(new StorageLocation("New Location", 1));
                FXUtil.runOnFXThread(() -> storageLocationTable.refresh());
            }
        });

        JFXButton editButton = new JFXButton("EDIT");
        editButton.getStyleClass().add("button-flat-gray");
        editButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                DisplayTaskRunner.getInstance().runTask(new GenericTask(() -> {
                    BarterStorageLocationEditCard editCard = new BarterStorageLocationEditCard(this, storageLocationTable.getSelectionModel().getSelectedItem());
                    FXUtil.runOnFXThread(editCard::show);
                }));
            }
        });

        editButton.disableProperty().bind(Bindings.isNull(storageLocationTable.getSelectionModel().selectedItemProperty()));

        buttonsContainer.getChildren().setAll(addButton, editButton);

    }

}
