package module.barter.display.storage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import common.jfx.FXUtil;
import common.jfx.LayoutBuilder;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import common.utilities.FileUtil;
import common.utilities.ToastUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import module.barter.model.BarterSettings;
import module.display.ToolView;

public class BarterSettingsToolView extends ToolView {

    private final ObjectProperty<BarterSettings> settings = new SimpleObjectProperty<>();
    private JFXToggleButton autofillAcceptGoodToggle;
    private JFXTextField shipWeightCapacityField;

    public BarterSettingsToolView() {
        super("Settings");

        VBox settingsContainer = new VBox(15);
        autofillAcceptGoodToggle = LayoutBuilder.createToggleButton("Auto Fill Accept Good", "Attempts to assist in entering data by auto-completing new a barter accept good field.", settingsContainer);
        shipWeightCapacityField = LayoutBuilder.createTextField("Ship Weight Capacity: ", "Set your ship's maximum weight capacity.", settingsContainer);
        getCard().setDisplayedContent(settingsContainer);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        Button saveButton = new JFXButton("SAVE");
        Button discardButton = new JFXButton("DISCARD");
        buttonContainer.getChildren().setAll(saveButton, discardButton);
        getCard().setFooterContent(buttonContainer);

        discardButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                FXUtil.runOnFXThread(this::setFieldsToSettings);
            }
        });

        saveButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                GenericTask task = new GenericTask(() -> {
                    settings.getValue().setAutofillAcceptGood(autofillAcceptGoodToggle.isSelected());
                    try {
                        settings.getValue().setShipWeightCapacity(Integer.parseInt(shipWeightCapacityField.getText()));
                    } catch (Exception ex) {
                        //invalid data.. ignore.
                    }

                    FileUtil.saveSettings(settings.getValue());
                    FXUtil.runOnFXThread(this::setFieldsToSettings);
                    ToastUtil.sendToast("Barter settings saved.");
                });
                BackgroundTaskRunner.getInstance().runTask(task);
            }
        });

        BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
            settings.set(BarterSettings.getSettings());
            FXUtil.runOnFXThread(() -> setFieldsToSettings());
        }));
    }

    private void setFieldsToSettings() {
        autofillAcceptGoodToggle.setSelected(settings.getValue().isAutofillAcceptGood());
        shipWeightCapacityField.setText(String.valueOf(settings.getValue().getShipWeightCapacity()));
    }
}
