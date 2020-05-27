package common.jfx.components;

import com.jfoenix.controls.JFXSpinner;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import javafx.beans.property.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class BackgroundLoadDataGraphic extends StackPane {

    private DoubleProperty size = new SimpleDoubleProperty(20);
    private BooleanProperty loading = new SimpleBooleanProperty(false);

    public BackgroundLoadDataGraphic(StringProperty textProperty, Runnable runnable) {
        loading.setValue(true);

        // Loading spinner
        JFXSpinner spinner = new JFXSpinner();
        spinner.radiusProperty().bind(size);
        spinner.visibleProperty().bind(loading);
        spinner.managedProperty().bind(loading);
        this.getChildren().add(spinner);

        Label label = new Label();
        label.textProperty().bind(textProperty);
        label.visibleProperty().bind(loading.not());
        label.managedProperty().bind(loading.not());
        this.getChildren().add(label);
        setAlignment(Pos.CENTER);

        BackgroundTaskRunner.getInstance().runTask(new GenericTask(() -> {
            runnable.run();
            loading.set(false);
        }));
    }

}
