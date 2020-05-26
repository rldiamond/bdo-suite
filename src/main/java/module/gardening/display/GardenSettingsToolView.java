package module.gardening.display;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import common.jfx.FXUtil;
import common.jfx.LayoutBuilder;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import common.utilities.FileUtil;
import common.utilities.ToastUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    private GardeningSettings settings;

    public GardenSettingsToolView() {
        super("Settings");

        VBox settingsContainer = new VBox(15);
        playerFence = LayoutBuilder.createComboBox("Fence Type: ", "Select the fence type you are gardening with.", Fence.getFences(), settingsContainer);
        numberOfFences = LayoutBuilder.createComboBox("Fence Number: ", "Select the number of fences you are using.", Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), settingsContainer);
        getCard().setDisplayedContent(settingsContainer);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        saveButton = new JFXButton("SAVE");
        discardButton = new JFXButton("DISCARD");
        buttonContainer.getChildren().setAll(saveButton, discardButton);
        getCard().setFooterContent(buttonContainer);

        //Load the settings
        settings = GardeningSettings.getSettings();

        //Set the fields
        setFieldsToSettings();

        //Add listeners to the buttons
        discardButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                FXUtil.runOnFXThread(this::setFieldsToSettings);
            }
        });
        saveButton.setOnMouseClicked(me -> {
            if (me.getButton().equals(MouseButton.PRIMARY)) {
                GenericTask task = new GenericTask(() -> {
                    settings.setNumberOfFences(Integer.parseInt(numberOfFences.getValue()));
                    settings.setPlayerFence(playerFence.getValue());
                    FileUtil.saveSettings(settings);
                    FXUtil.runOnFXThread(this::setFieldsToSettings);
                    ToastUtil.sendToast("Gardening settings saved.");
                });
                BackgroundTaskRunner.getInstance().runTask(task);
            }
        });
    }

    private void setFieldsToSettings() {
        playerFence.getItems().stream().filter(fence -> fence.getName().equalsIgnoreCase(settings.getPlayerFence().getName())).findAny().ifPresent(playerFence.getSelectionModel()::select);
        numberOfFences.getSelectionModel().select(String.valueOf(settings.getNumberOfFences()));
    }

    
}
