package module.barter;

import common.algorithm.AlgorithmException;
import module.barter.algorithms.BarterAlgorithm;
import module.barter.model.BarterGood;
import module.barter.model.BarterLevel;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;

import java.util.List;

public class BarterOptimizer {

    public static BarterPlan optimize(List<BarterRoute> possibleRoutes, List<BarterLevel> barterLevels, List<BarterGood> barterGoods) {
        BarterAlgorithm algorithm =  new BarterAlgorithm(possibleRoutes);

        try {
            return algorithm.run();
        } catch (AlgorithmException ex) {
            return null; //TODO
        }
    }

}
