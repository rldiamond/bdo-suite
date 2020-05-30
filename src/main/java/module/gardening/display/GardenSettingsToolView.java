package module.gardening.display;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import module.display.ToolView;
import module.gardening.model.Fence;
import module.gardening.model.GardeningSettings;

import java.util.Arrays;

public class GardenSettingsToolView extends ToolView {

    private JFXComboBox<Fence> playerFence;
    private JFXComboBox<String> numberOfFences;
    private Button saveButton;
    private Button discardButton;
    private ObjectProperty<GardeningSettings> settings = new SimpleObjectProperty<>();

    public GardenSettingsToolView() {
        super("Settings");

        VBox settingsContainer = new VBox(15);
        playerFence = LayoutBuilder.createComboBox("Fence Type: ", "Select the fence type you are gardening with.", Fence.getFences(), settingsContainer);
        numberOfFences = LayoutBuilder.createComboBox("Fence Number: ", "Select the number of fences you are using.", Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), settingsContainer);
        getCard().setDisplayedContent(settingsContainer);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        saveButton = new JFXButton("SAVE");
        Tooltip.install(saveButton, new Tooltip("Save settings. Profitability analysis will auto-update within 5 minutes. Click Refresh to force update."));
        discardButton = new JFXButton("DISCARD");
        Tooltip.install(discardButton, new Tooltip("Discard settings."));
        buttonContainer.getChildren().setAll(saveButton, discardButton);
        getCard().setFooterContent(buttonContainer);

        //Load the settings
        BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
            settings.setValue(GardeningSettings.getSettings());
            FXUtil.runOnFXThread(this::setFieldsToSettings);
        }));

        //Add listeners to the buttons
        discardButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                FXUtil.runOnFXThread(this::setFieldsToSettings);
            }
        });
        saveButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                GenericTask task = new GenericTask(() -> {
                    settings.getValue().setNumberOfFences(Integer.parseInt(numberOfFences.getValue()));
                    settings.getValue().setPlayerFence(playerFence.getValue());
                    FileUtil.saveSettings(settings.getValue());
                    FXUtil.runOnFXThread(this::setFieldsToSettings);
                    ToastUtil.sendToast("Gardening settings saved.");
                });
                BackgroundTaskRunner.getInstance().runTask(task);
            }
        });
    }

    private void setFieldsToSettings() {
        playerFence.getItems().stream().filter(fence -> fence.getName().equalsIgnoreCase(settings.getValue().getPlayerFence().getName())).findAny().ifPresent(playerFence.getSelectionModel()::select);
        numberOfFences.getSelectionModel().select(String.valueOf(settings.getValue().getNumberOfFences()));
    }

    
}
