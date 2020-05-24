package module.barter.task;

import common.algorithm.AlgorithmException;
import common.task.BackgroundTask;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.TextArea;
import module.barter.algorithms.BarterAlgorithm;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BarterOptimizationTask implements BackgroundTask {

    private static final Logger logger = LogManager.getLogger(BarterOptimizationTask.class);
    private final TextArea console;
    private final List<BarterRoute> barterRoutes;
    private final BooleanProperty busyProperty;

    public BarterOptimizationTask(List<BarterRoute> barterRouteList, TextArea console, BooleanProperty busyProperty) {
        this.console = console;
        this.barterRoutes = barterRouteList;
        this.busyProperty = busyProperty;
    }

    @Override
    public void run() {
        BarterAlgorithm barterAlgorithm = new BarterAlgorithm(barterRoutes);
        try {
            BarterPlan barterPlan = barterAlgorithm.run();
            console.setText(barterPlan.toString());
        } catch (AlgorithmException ex) {
            logger.error("Failed to execute the barter algorithm.", ex);
            console.setText("An error occurred executing the barter optimization process.");
        }
        busyProperty.setValue(false);
    }
}
