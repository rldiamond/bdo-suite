package module.barter.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;
import module.barter.model.BarterTier;
import module.barter.model.PlannedRoute;

import java.util.List;

/**
 * The alpha for the barter algorithm assumes only one possible barter path.
 * Does not optimize early routes for later routes.
 */
public class BarterAlgorithmBeta implements Algorithm<BarterPlan> {

    private final List<BarterRoute> possibleRoutes;
    private final double shipCapacity;

    public BarterAlgorithmBeta(List<BarterRoute> possibleRoutes, double shipCapacity) {
        this.possibleRoutes = possibleRoutes;
        this.shipCapacity = shipCapacity;
    }


    @Override
    public BarterPlan run() throws AlgorithmException {
        BarterPlan barterPlan = new BarterPlan();
        BarterRoute level1Route = findBarterRoute(BarterTier.ONE);
        BarterRoute level2Route = findBarterRoute(BarterTier.TWO);

        // Figure the first barter
        double neededLevel1Goods = level2Route.getExchanges() * level2Route.getAcceptAmount();
        double maxLevel1Goods = level1Route.getExchanges() * level1Route.getExchangeAmount();

        double level1Goods;

        if (neededLevel1Goods <= maxLevel1Goods) {
            // Perform X barters to get full amount
            double exchangesToPerform = neededLevel1Goods / level1Route.getExchangeAmount();
            double goodsToTurnIn = exchangesToPerform * level1Route.getAcceptAmount();
            double goodsToReceive = exchangesToPerform * level1Route.getExchangeAmount();
            String description = "Perform " + exchangesToPerform + " exchanges with " + goodsToTurnIn +
                    " Tier " + level1Route.getAcceptTier() + " goods to receive " + goodsToReceive +" Tier " +
                    level1Route.getExchangeTier() + " goods.";
            barterPlan.addRoute(new PlannedRoute(description, exchangesToPerform));
            level1Goods = goodsToReceive;

        } else {
            double exchangesToPerform = level1Route.getExchangeAmount();
            double goodsToTurnIn = exchangesToPerform * level1Route.getAcceptAmount();
            double goodsToReceive = exchangesToPerform * level1Route.getExchangeAmount();
            String description = "Perform " + exchangesToPerform + " exchanges with " + goodsToTurnIn +
                    " Tier " + level1Route.getAcceptTier() + " goods to receive " + goodsToReceive +" Tier " +
                    level1Route.getExchangeTier() + " goods.";
            barterPlan.addRoute(new PlannedRoute(description, exchangesToPerform));
            level1Goods = goodsToReceive;
        }

        // Figure the second barter
        // how many exchanges can i perform of the second tier?
        double maxLevel2Exchanges = level1Goods / level2Route.getAcceptAmount();
        double exchanges;
        if (maxLevel2Exchanges <= level2Route.getExchanges()) {
            exchanges = maxLevel2Exchanges;
        } else {
            exchanges = level2Route.getExchanges();
        }
        double level2Goods = level2Route.getExchangeAmount() * exchanges;
        double level1GoodsTurnIn = exchanges * level2Route.getAcceptAmount();
        String description = "Perform " + exchanges + " exchanges with " + level1GoodsTurnIn +
                " Tier " + level2Route.getAcceptTier() + " goods to receive " + level2Goods +" Tier " +
                level2Route.getExchangeTier() + " goods.";
        barterPlan.addRoute(new PlannedRoute(description, exchanges));

        // Figure third barter
        BarterRoute level3Route = findBarterRoute(BarterTier.THREE);
        double maxLevel3Exchanges = level2Goods / level3Route.getAcceptAmount();
        double exchanges3;
        if (maxLevel3Exchanges <= level3Route.getExchanges()) {
            exchanges3 = maxLevel3Exchanges;
        } else {
            exchanges3 = level3Route.getExchanges();
        }
        double level3Goods = level3Route.getExchangeAmount() * exchanges3;
        double level2GoodsTurnIn = exchanges3 * level3Route.getAcceptAmount();
        description = "Perform " + exchanges3 + " exchanges with " + level2GoodsTurnIn +
                " Tier " + level3Route.getAcceptTier() + " goods to receive " + level3Goods +" Tier " +
                level3Route.getExchangeTier() + " goods.";
        barterPlan.addRoute(new PlannedRoute(description, exchanges));

        // Figure fourth barter
        BarterRoute level4Route = findBarterRoute(BarterTier.FOUR);
        double maxLevel4Exchanges = level3Goods / level4Route.getAcceptAmount();
        double exchanges4;
        if (maxLevel4Exchanges <= level4Route.getExchanges()) {
            exchanges4 = maxLevel3Exchanges;
        } else {
            exchanges4 = level4Route.getExchanges();
        }
        double level4Goods = level4Route.getExchangeAmount() * exchanges4;
        double level3GoodsTurnIn = exchanges4 * level4Route.getAcceptAmount();
        description = "Perform " + exchanges4 + " exchanges with " + level3GoodsTurnIn +
                " Tier " + level4Route.getAcceptTier() + " goods to receive " + level4Goods +" Tier " +
                level4Route.getExchangeTier() + " goods.";
        barterPlan.addRoute(new PlannedRoute(description, exchanges));

        // Figure fifth barter
        BarterRoute level5Route = findBarterRoute(BarterTier.FIVE);
        double maxLevel5Exchanges = level4Goods / level5Route.getAcceptAmount();
        double exchanges5;
        if (maxLevel5Exchanges <= level4Route.getExchanges()) {
            exchanges5 = maxLevel3Exchanges;
        } else {
            exchanges5 = level4Route.getExchanges();
        }
        double level5Goods = level5Route.getExchangeAmount() * exchanges5;
        double level4GoodsTurnIn = exchanges5 * level5Route.getAcceptAmount();
        description = "Perform " + exchanges5 + " exchanges with " + level4GoodsTurnIn +
                " Tier " + level5Route.getAcceptTier() + " goods to receive " + level5Goods +" Tier " +
                level5Route.getExchangeTier() + " goods.";
        barterPlan.addRoute(new PlannedRoute(description, exchanges));

        return barterPlan;
    }

    private BarterRoute findBarterRoute(BarterTier exchangeTier) throws AlgorithmException {
        return possibleRoutes.stream().filter(route -> route.getExchangeTier().equals(exchangeTier)).findFirst().orElseThrow(AlgorithmException::new);
    }




}
