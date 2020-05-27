package module.barter.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.barter.model.*;
import module.marketapi.algorithms.CrowCoinValueAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NewBarterAlgorithm implements Algorithm<BarterPlan> {

    private static final double MAX_PARLEY = 1000000;
    private static final double VP_PARLEY_DISCOUNT_RATE = 0.10;
    private final List<Barter> barters;
    private List<Barter> completedBarters;

    private BarterPlan barterPlan;

    public NewBarterAlgorithm(List<Barter> barters) {
        this.barters = barters;
    }

    @Override
    public BarterPlan run() throws AlgorithmException {
        barterPlan = new BarterPlan();
        completedBarters = new ArrayList<>();

        // We will work in reverse, from crow coins, as they are most valuable.
        for (Barter barter : findBartersWithExchangeTier(BarterLevelType.CROW_COIN)) {
            Barter firstBarter = findFirstBarterInChain(barter);
            createPlannedRoutesFromBarter(firstBarter).forEach(barterPlan::addRoute);
        }


        return barterPlan;
    }

    /**
     * Create an entire route plan from the first barter in a chain.
     * <p>
     * NOTE: This version of the barter optimization algorithm does not take into account:
     * Ship weight, ship capacity, items in storage, if enough parley is available.
     *
     * @param firstBarter The first barter in a chain to create a route with.
     * @return An entire route plan.
     */
    private List<PlannedRoute> createPlannedRoutesFromBarter(Barter firstBarter) throws AlgorithmException {
        List<PlannedRoute> plannedRoutes = new ArrayList<>();

        //calculate the first barter
        //Barter firstBarter = findBarterAcceptingLevel(BarterLevelType.ZERO).orElseThrow(AlgorithmException::new);
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
        //barterPlan.addParley(firstBarter.getParley() * (int) exchanges);
        //barterPlan.addRoute(route);
        plannedRoutes.add(route);
        this.completedBarters.add(firstBarter);



        return plannedRoutes;
    }

    private Barter findNextBarter(Barter barter) {
        
    }

    private PlannedRoute createRouteFromPrevious(PlannedRoute previousRoute) throws AlgorithmException {
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

    private Barter findFirstBarterInChain(Barter barter) {
        // find previous barter
        if (findBarterExchangingGood(barter.getAcceptGoodName()).isPresent()) {
            Barter previousBarter = findBarterExchangingGood(barter.getAcceptGoodName()).get();
            return findFirstBarterInChain(previousBarter);
        } else {
            return barter;
        }
    }

    /**
     * Calculate the total parley required based on the number of exchanges to perform,
     * the required parley per exchange, taking into account if the player has a value pack.
     *
     * @param requiredParley The amount of parley required for one exchange.
     * @param exchanges      The amount of exchanges to calculate total parley for.
     * @return Total parley cost to perform said exchanges.
     */
    private double calculateParley(double requiredParley, int exchanges) {
        // User gets a parley discount if they have an active value pack.
        if (BarterSettings.getSettings().hasValuePack()) {
            requiredParley = requiredParley - (requiredParley * VP_PARLEY_DISCOUNT_RATE);
        }
        return requiredParley * exchanges;
    }

    private List<Barter> findBartersWithExchangeTier(BarterLevelType levelType) {
        return barters.stream().filter(barter -> {
            return BarterGood.getBarterGoodByName(barter.getExchangeGoodName()).get().getLevel().equals(levelType);
        }).collect(Collectors.toList());
    }

    private Optional<Barter> findBarterAcceptingGood(String goodName) {
        return barters.stream().filter(barter -> barter.getAcceptGoodName().equalsIgnoreCase(goodName))
                .findAny();
    }

    private Optional<Barter> findBarterExchangingGood(String goodName) {
        return barters.stream().filter(barter -> barter.getExchangeGoodName().equalsIgnoreCase(goodName))
                .findAny();
    }

    private Optional<Barter> findBarterAcceptingLevel(BarterLevelType levelType) {
        return barters.stream().filter(barter -> {
            return BarterGood.getBarterGoodByName(barter.getAcceptGoodName()).get().getLevel().equals(levelType);
        }).findAny();
    }

    private Optional<Barter> findBarterExchangeLevel(BarterLevelType levelType) {
        return barters.stream().filter(barter -> {
            return BarterGood.getBarterGoodByName(barter.getAcceptGoodName()).get().getLevel().equals(levelType);
        }).findAny();
    }

}
