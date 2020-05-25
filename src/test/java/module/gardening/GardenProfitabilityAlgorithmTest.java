package module.gardening;

import common.utilities.TextUtil;
import module.gardening.algorithms.GardenProfitabilityAlgorithm;
import module.gardening.model.CropAnalysis;
import module.gardening.model.Fence;
import org.junit.Test;

import java.util.List;

public class GardenProfitabilityAlgorithmTest {

    @Test
    public void test() throws Exception {
        Fence playerFence = new Fence();
        playerFence.setName("Strong Fence");
        playerFence.setGrids(10);
        GardenProfitabilityAlgorithm gardenProfitabilityAlgorithm = new GardenProfitabilityAlgorithm(0.35,.305,playerFence, 10);
        List<CropAnalysis> cropAnalyses = gardenProfitabilityAlgorithm.run();
        System.out.println(TextUtil.formatAsSilver(cropAnalyses.get(0).getValuePerHarvest()));
    }

}
