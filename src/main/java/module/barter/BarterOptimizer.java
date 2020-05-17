package module.barter;

import common.algorithm.AlgorithmException;
import module.barter.algorithms.BarterAlgorithmAlpha;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;
import module.barter.model.BarterTier;
import module.barter.model.PlannedRoute;

import java.util.ArrayList;
import java.util.Collections;
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
