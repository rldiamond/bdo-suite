package module.barter;

import common.algorithm.AlgorithmException;
import module.barter.algorithms.BarterAlgorithmAlpha;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;

import java.util.List;

public class BarterOptimizer {

    public static BarterPlan optimize(List<BarterRoute> possibleRoutes) {
        BarterAlgorithmAlpha algorithm = new BarterAlgorithmAlpha(possibleRoutes);

        try {
            return algorithm.run();
        } catch (AlgorithmException ex) {
            return null;
        }
    }

}
