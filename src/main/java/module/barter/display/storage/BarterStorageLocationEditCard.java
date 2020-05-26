package module.barter.display.storage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import common.jfx.LayoutBuilder;
import common.jfx.components.Card;
import common.jfx.components.dialog.ActionableAlertDialog;
import common.jfx.components.dialog.AlertDialogType;
import common.utilities.TextUtil;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import module.barter.model.BarterModuleEvent;
import module.barter.model.StorageLocation;

public class BarterStorageLocationEditCard extends JFXDialog {

    private final StorageLocation storageLocation;

    public BarterStorageLocationEditCard(StackPane container, StorageLocation storageLocation) {
        setTransitionType(DialogTransition.BOTTOM);
        setDialogContainer(container);

        Card card = new Card("Edit Location: " + storageLocation.getName());
        card.setPrefSize(450, 300);
        this.storageLocation = storageLocation;

        VBox settingsContainer = new VBox(10);
        JFXTextField locationNameField = LayoutBuilder.createTextField("Name: ", "Enter the name to call this location.", settingsContainer);
        JFXTextField storageCapacityField = LayoutBuilder.createTextField("Storage Capacity: ", "Enter the total number of storage slots at this location.", settingsContainer);
        card.setDisplayedContent(settingsContainer);

        //set data
        locationNameField.setText(storageLocation.getName());
        storageCapacityField.setText(String.valueOf(storageLocation.getStorage().getCapacity()));

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);

        // Save
        JFXButton saveButton = new JFXButton("SAVE");
        saveButton.getStyleClass().add("button-flat-gray");
        saveButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                storageLocation.setName(TextUtil.toTitleCase(locationNameField.getText()));
                try {
                    int capacity = Integer.parseInt(storageCapacityField.getText());
                    storageLocation.getStorage().setCapacity(capacity);
                } catch (Exception ex) {
                    // invalid capacity entry.
                }
                fireEvent(new BarterModuleEvent(BarterModuleEvent.ModuleEventType.SAVE));
                this.close();
            }
        });

        // Cancel
        JFXButton cancelButton = new JFXButton("CANCEL");
        cancelButton.getStyleClass().add("button-flat-gray");
        cancelButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                this.close();
            }
        });

        // Delete
        JFXButton deleteButton = new JFXButton("DELETE");
        deleteButton.getStyleClass().add("button-flat-red");
        buttonContainer.getChildren().setAll(saveButton, cancelButton, deleteButton);
        card.setFooterContent(buttonContainer);
        deleteButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                ActionableAlertDialog alert = new ActionableAlertDialog(AlertDialogType.CONFIRMATION);
                alert.setActionButtonText("Confirm");
                alert.setTitle("Are you sure?");
                alert.setBody("If the location is removed, all stored item data will be lost. This action cannot be undone.");
                alert.setAction(() -> {
                    fireEvent(new BarterModuleEvent(BarterModuleEvent.ModuleEventType.DELETE));
                    this.close();
                });
                alert.show();
            }
        });

        setContent(card);

    }

}
