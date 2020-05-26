package module.barter.display.storage;

import com.jfoenix.controls.JFXSpinner;
import common.jfx.FXUtil;
import common.jfx.components.Card;
import common.task.DisplayTaskRunner;
import common.task.GenericTask;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import module.barter.model.StorageLocation;

import java.util.concurrent.TimeUnit;

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

        try {
            TimeUnit.SECONDS.sleep(5);
            FXUtil.runOnFXThread(() -> {
                getChildren().add(card);
                loading.set(false);
            });
        } catch (Exception ex) {

        }
    }

}
