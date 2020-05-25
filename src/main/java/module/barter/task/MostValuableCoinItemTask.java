package module.barter.task;

import common.algorithm.AlgorithmException;
import common.jfx.FXUtil;
import common.task.BackgroundTask;
import common.utilities.ToastUtil;
import data.image.ImageDAO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import module.barter.algorithms.MostValuableCoinItemAlgorithm;
import module.marketapi.model.CrowCoinVendorItem;

public class MostValuableCoinItemTask extends BackgroundTask {

    private final StringProperty textProperty;
    private final BooleanProperty busyProperty;
    private final ObjectProperty<Image> imageProperty;

    public MostValuableCoinItemTask(StringProperty text, ObjectProperty<Image> imageProperty, BooleanProperty busyProperty) {
        this.textProperty = text;
        this.busyProperty = busyProperty;
        this.imageProperty = imageProperty;
    }

    @Override
    public void doTask() {
        busyProperty.setValue(true);
        MostValuableCoinItemAlgorithm algorithm = new MostValuableCoinItemAlgorithm();
        try {
            CrowCoinVendorItem item = algorithm.run();
            Image itemImage = ImageDAO.getInstance().getImage(item.getItemNumber());
            FXUtil.runOnFXThread(() -> {
                textProperty.setValue(item.getItemName());
                imageProperty.setValue(itemImage);
                busyProperty.set(false);
                ToastUtil.sendToast("Crow Coin item has been updated.");
            });
        } catch (AlgorithmException ex) {
            textProperty.setValue("NA");
            busyProperty.set(false);
        }
    }
}
