package common.jfx.components;

import com.jfoenix.controls.JFXSpinner;
import common.jfx.FXUtil;
import common.task.BackgroundTaskRunner;
import common.task.GenericTask;
import data.image.ImageDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ItemImage extends StackPane {

    private DoubleProperty size = new SimpleDoubleProperty(25);
    private BooleanProperty loading = new SimpleBooleanProperty(false);

    public ItemImage(long itemId, double size) {
        this(itemId);
        this.size.setValue(size);
    }

    public ItemImage(long itemId) {
        // Configuration
        this.prefHeightProperty().bind(size);
        this.prefWidthProperty().bind(size);
        this.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        this.setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        loading.setValue(true);

        // Loading spinner
        JFXSpinner spinner = new JFXSpinner();
        spinner.radiusProperty().bind(size);
        spinner.visibleProperty().bind(loading);
        spinner.managedProperty().bind(loading);
        this.getChildren().add(spinner);

        // Image
        GenericTask imageTask = new GenericTask(() -> {
            final Image image = ImageDAO.getInstance().getImage(itemId);
            ImageView imageView = new ImageView();
            if (image != null) {
                imageView.fitWidthProperty().bind(size);
                imageView.fitHeightProperty().bind(size);
                imageView.visibleProperty().bind(loading.not());
                imageView.managedProperty().bind(loading.not());
                imageView.setImage(image);
            }
            FXUtil.runOnFXThread(() -> {
                this.getChildren().add(imageView);
                loading.setValue(false);
            });
        });
        BackgroundTaskRunner.getInstance().runTask(imageTask);
    }

    /**
     * Set the size of the image.
     * @param size
     */
    public void setSize(double size) {
        this.size.setValue(size);
    }

}
