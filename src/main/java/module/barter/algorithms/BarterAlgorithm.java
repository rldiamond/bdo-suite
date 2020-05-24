package module.barter.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.barter.model.*;
import module.marketapi.algorithms.CrowCoinValueAlgorithm;

import java.util.List;
import java.util.Optional;

/**
 * Note: Still assuming only one possible barter route.
 */
public class BarterAlgorithm implements Algorithm<BarterPlan> {

    private final List<Barter> barters;
    private final BarterPlan barterPlan;


    public BarterAlgorithm(List<Barter> barters) {
        this.barters = barters;
        this.barterPlan = new BarterPlan();
    }

    /**
     * @inhertDoc
     */
    @Override
    public BarterPlan run() throws AlgorithmException {

        Barter firstBarter = findBarterAcceptingLevel(BarterLevelType.ZERO).orElseThrow(AlgorithmException::new);
        Barter secondBarter = findBarterAcceptingGood(firstBarter.getExchangeGoodName()).orElseThrow(AlgorithmException::new);

        double optimalAcceptedGoods = secondBarter.getAcceptAmount() * secondBarter.getExchanges();
        double maximumAcceptedGoods = firstBarter.getExchangeAmount() * firstBarter.getExchanges();

        PlannedRoute route = new PlannedRoute();
        route.setTurnInGood(BarterGood.getBarterGoodByName(firstBarter.getAcceptGoodName()).orElseThrow(AlgorithmException::new));
        route.setReceivedGood(BarterGood.getBarterGoodByName(firstBarter.getExchangeGoodName()).orElseThrow(AlgorithmException::new));

        // How many exchanges do we do to maximize the second barter?
        double exchanges;
        if (optimalAcceptedGoods <= maximumAcceptedGoods) {
            // We can full perform the second barter.
            // so we need to figure out what that is
            exchanges = optimalAcceptedGoods / firstBarter.getExchangeAmount();
        } else {
            exchanges = maximumAcceptedGoods / firstBarter.getExchangeAmount();
        }
        route.setExchanges((int) exchanges);
        route.setTurnInAmount((int) (firstBarter.getAcceptAmount() * exchanges));
        route.setReceivedAmount((int)(exchanges * firstBarter.getExchangeAmount()));
        barterPlan.addRoute(route);

        //Create route for L1-L2
        PlannedRoute level2Route = createRoute(route);
        barterPlan.addRoute(level2Route);
        //Create route for L2-L3
        PlannedRoute level3Route = createRoute(level2Route);
        barterPlan.addRoute(level3Route);
        //Create route for L3-L4
        PlannedRoute level4Route = createRoute(level3Route);
        barterPlan.addRoute(level4Route);
        //Create route for L4-L5
        PlannedRoute level5Route = createRoute(level4Route);
        barterPlan.addRoute(level5Route);
        //Create route for L5-CC
        PlannedRoute ccRoute = createRoute(level5Route);
        barterPlan.addRoute(ccRoute);

        return barterPlan;
    }

    private PlannedRoute createRoute(PlannedRoute previousRoute) throws AlgorithmException {
        PlannedRoute route = new PlannedRoute();

        //find the next barter.
        Barter barter = findBarterAcceptingGood(previousRoute.getReceivedGood().getName()).orElseThrow(AlgorithmException::new);
        route.setTurnInGood(previousRoute.getReceivedGood());
        route.setReceivedGood(BarterGood.getBarterGoodByName(barter.getExchangeGoodName()).orElseThrow(AlgorithmException::new));

        double optimalAcceptedGoods = barter.getAcceptAmount() * barter.getExchanges();

        double exchanges;
        if (optimalAcceptedGoods <= previousRoute.getReceivedAmount()) {
            exchanges = optimalAcceptedGoods / barter.getAcceptAmount();
        } else {
            exchanges = (double) previousRoute.getReceivedAmount() / barter.getAcceptAmount();
        }
        route.setExchanges((int) exchanges);
        route.setTurnInAmount((int) (barter.getAcceptAmount() * exchanges));
        route.setReceivedAmount((int)(exchanges*barter.getExchangeAmount()));

        //calculate some profit
        if (previousRoute.getReceivedAmount() > route.getTurnInAmount()) {
            int excessGoods = previousRoute.getReceivedAmount() - route.getTurnInAmount();
            double valueOfGood = BarterLevel.getBarterLevelByType(previousRoute.getReceivedGood().getLevel()).getValue();
            barterPlan.addProfit(valueOfGood * excessGoods);
        }
        if (route.getReceivedGood().getLevel().equals(BarterLevelType.CROW_COIN)) {
            CrowCoinValueAlgorithm crowCoinValueAlgorithm = new CrowCoinValueAlgorithm();
            double coinValue = crowCoinValueAlgorithm.run();
            barterPlan.addProfit(coinValue * route.getReceivedAmount());
        }

        return route;
    }

    private Optional<Barter> findBarterAcceptingGood(String goodName) {
        return barters.stream().filter(barter -> barter.getAcceptGoodName().equalsIgnoreCase(goodName))
                .findAny();
    }

    private Optional<Barter> findBarterAcceptingLevel(BarterLevelType levelType) {
        return barters.stream().filter(barter -> {
            return BarterGood.getBarterGoodByName(barter.getAcceptGoodName()).get().getLevel().equals(levelType);
        }).findAny();
    }

}
