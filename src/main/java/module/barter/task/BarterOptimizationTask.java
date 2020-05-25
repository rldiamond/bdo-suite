package module.barter.task;

import common.algorithm.AlgorithmException;
import common.task.BackgroundTask;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import module.barter.algorithms.BarterAlgorithm;
import module.barter.model.Barter;
import module.barter.model.BarterPlan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BarterOptimizationTask extends BackgroundTask {

    private static final Logger logger = LogManager.getLogger(BarterOptimizationTask.class);
    private final ObjectProperty<BarterPlan> barterPlanProperty;
    private final List<Barter> barters;
    private final BooleanProperty busyProperty;

    public BarterOptimizationTask(List<Barter> barterList, ObjectProperty<BarterPlan> barterPlanProperty, BooleanProperty busyProperty) {
        this.barterPlanProperty = barterPlanProperty;
        this.barters = barterList;
        this.busyProperty = busyProperty;
    }

    public void doTask() {
        BarterAlgorithm barterAlgorithm = new BarterAlgorithm(barters);
        try {
            BarterPlan barterPlan = barterAlgorithm.run();
            barterPlanProperty.setValue(barterPlan);
        } catch (AlgorithmException ex) {
            logger.error("Failed to execute the barter algorithm.", ex);
            barterPlanProperty.setValue(null);
        }
        busyProperty.setValue(false);
    }
}
