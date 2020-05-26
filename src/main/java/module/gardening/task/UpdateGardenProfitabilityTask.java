package module.gardening.task;

import common.algorithm.AlgorithmException;
import common.task.BackgroundTask;
import common.utilities.ToastUtil;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import module.gardening.algorithms.GardenProfitabilityAlgorithm;
import module.gardening.model.CropAnalysis;
import module.gardening.model.GardeningSettings;
import module.gardening.model.HarvestSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class UpdateGardenProfitabilityTask extends BackgroundTask {

    private final Logger logger = LogManager.getLogger(UpdateGardenProfitabilityTask.class);

    private final ObservableList<CropAnalysis> cropAnalyses;
    private final BooleanProperty busy;

    public UpdateGardenProfitabilityTask(ObservableList<CropAnalysis> cropAnalyses, BooleanProperty busy) {
        this.cropAnalyses = cropAnalyses;
        this.busy = busy;
    }

    @Override
    public void doTask() {
        busy.setValue(true);
        GardenProfitabilityAlgorithm algorithm = new GardenProfitabilityAlgorithm(HarvestSettings.marketTax, HarvestSettings.playerBonus, GardeningSettings.getSettings().getPlayerFence(), GardeningSettings.getSettings().getNumberOfFences());
        List<CropAnalysis> analyses = Collections.EMPTY_LIST;
        try {
            analyses = algorithm.run();
        } catch (AlgorithmException ex) {
            logger.error("Failed to run the gardening profitability algorithm.", ex);
        }

        cropAnalyses.clear();
        cropAnalyses.addAll(analyses);
        busy.setValue(false);
        ToastUtil.sendToast("Crop profitability has been updated.");
    }
}
