package module.barter.task;

import common.algorithm.AlgorithmException;
import common.task.BackgroundTask;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import module.barter.algorithms.MostValuableCoinItemAlgorithm;
import module.marketapi.model.CrowCoinVendorItem;

public class MostValuableCoinItemTask implements BackgroundTask {

    private final StringProperty textProperty;
    private final BooleanProperty busyProperty;

    public MostValuableCoinItemTask(StringProperty text, BooleanProperty busyProperty) {
        this.textProperty = text;
        this.busyProperty = busyProperty;
    }

    @Override
    public void run() {
        MostValuableCoinItemAlgorithm algorithm = new MostValuableCoinItemAlgorithm();
        try {
            CrowCoinVendorItem item = algorithm.run();
            textProperty.setValue(item.getItemName());
        } catch (AlgorithmException ex) {
            textProperty.setValue("NA");
        }
        busyProperty.set(false);
    }
}
