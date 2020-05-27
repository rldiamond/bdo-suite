package module.barter.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.barter.model.Barter;
import module.barter.model.BarterPlan;

import java.util.List;

public class NewBarterAlgorithm implements Algorithm<BarterPlan> {

    private final List<Barter> barters;
    private final BarterPlan barterPlan;

    public NewBarterAlgorithm(List<Barter> barters) {
        this.barters = barters;
        barterPlan = new BarterPlan();
    }

    @Override
    public BarterPlan run() throws AlgorithmException {
        return null;
    }
}
