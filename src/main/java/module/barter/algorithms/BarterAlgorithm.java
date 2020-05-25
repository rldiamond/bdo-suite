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
        barterPlan.addParley(firstBarter.getParley() * (int) exchanges);
        barterPlan.addRoute(route);
        barters.remove(firstBarter);

        PlannedRoute previousRoute = route;
        for (Barter barter : barters) {
            PlannedRoute plannedRoute = createRoute(previousRoute);
            barterPlan.addRoute(plannedRoute);
            previousRoute = plannedRoute;
        }

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
        barterPlan.addParley(barter.getParley() * (int) exchanges);

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
