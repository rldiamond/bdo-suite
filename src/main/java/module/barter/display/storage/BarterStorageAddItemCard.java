package module.barter.display.storage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import common.jfx.LayoutBuilder;
import common.jfx.components.AutoCompleteComboBox;
import common.jfx.components.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import module.barter.model.BarterGood;
import module.barter.model.BarterModuleEvent;
import module.barter.model.StorageLocation;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BarterStorageAddItemCard extends JFXDialog {

    public BarterStorageAddItemCard(StackPane container, StorageLocation storageLocation) {
        setTransitionType(DialogTransition.BOTTOM);
        setDialogContainer(container);

        Card card = new Card("Add Item to " + storageLocation.getName() + " Storage");
        card.setPrefSize(375, 200);

        StackPane settingsContainer = new StackPane();
        settingsContainer.setAlignment(Pos.CENTER);
        settingsContainer.setPadding(new Insets(0,0,0,20));
        final List<String> barterGoods = BarterGood.getBarterGoods().stream().map(BarterGood::getName).collect(Collectors.toList());
        Collections.sort(barterGoods);
        JFXComboBox<String> itemComboBox = LayoutBuilder.createComboBox("Choose Item: ", "Select an item to add to storage.", barterGoods, settingsContainer);
        AutoCompleteComboBox.install(itemComboBox);
        card.setDisplayedContent(settingsContainer);

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);

        // Save
        JFXButton saveButton = new JFXButton("ADD ITEM");
        Tooltip.install(saveButton, new Tooltip("Add the selected item to this storage."));
        saveButton.getStyleClass().add("button-flat-green");
        saveButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                BarterGood.getBarterGoodByName(itemComboBox.getSelectionModel().getSelectedItem()).ifPresent(good -> {
                    storageLocation.getStorage().addItem(good, 1);
                });
                fireEvent(new BarterModuleEvent(BarterModuleEvent.ModuleEventType.SAVE));
                this.close();
            }
        });

        // Cancel
        JFXButton cancelButton = new JFXButton("CANCEL");
        Tooltip.install(cancelButton, new Tooltip("Don't add items to this storage."));
        cancelButton.getStyleClass().add("button-flat-gray");
        cancelButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                this.close();
            }
        });
        buttonContainer.getChildren().setAll(saveButton, cancelButton);
        card.setFooterContent(buttonContainer);

        setContent(card);
    }

}
