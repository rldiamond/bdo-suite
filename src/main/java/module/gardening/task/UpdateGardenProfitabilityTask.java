package module.gardening.task;

import common.algorithm.AlgorithmException;
import common.task.BackgroundTask;
import common.utilities.ToastUtil;
import javafx.collections.ObservableList;
import module.gardening.algorithms.GardenProfitabilityAlgorithm;
import module.gardening.model.CropAnalysis;
import module.gardening.model.Fence;
import module.gardening.model.HarvestSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

public class UpdateGardenProfitabilityTask extends BackgroundTask {

    private final Logger logger = LogManager.getLogger(UpdateGardenProfitabilityTask.class);

    private final ObservableList<CropAnalysis> cropAnalyses;

    public UpdateGardenProfitabilityTask(ObservableList<CropAnalysis> cropAnalyses) {
        this.cropAnalyses = cropAnalyses;
    }

    @Override
    public void doTask() {
        Fence fence = new Fence();
        fence.setGrids(10);
        fence.setName("Strong Fence");
        GardenProfitabilityAlgorithm algorithm = new GardenProfitabilityAlgorithm(HarvestSettings.marketTax, HarvestSettings.playerBonus, fence, HarvestSettings.amountOfFences);
        List<CropAnalysis> analyses = Collections.EMPTY_LIST;
        try {
            analyses = algorithm.run();
        } catch (AlgorithmException ex) {
            logger.error("Failed to run the gardening profitability algorithm.", ex);
        }

        cropAnalyses.clear();
        cropAnalyses.addAll(analyses);
        ToastUtil.sendToast("Crop profitability has been updated.");
    }
}
