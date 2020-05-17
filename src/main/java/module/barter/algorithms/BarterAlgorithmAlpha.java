package module.barter.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.barter.model.BarterPlan;
import module.barter.model.BarterRoute;
import module.barter.model.BarterTier;
import module.barter.model.PlannedRoute;

import java.util.ArrayList;
import java.util.List;

/**
 * The alpha for the barter algorithm assumes only one possible barter path.
 * Does not optimize early routes for later routes.
 */
public class BarterAlgorithmAlpha implements Algorithm<BarterPlan> {

    private final List<BarterRoute> possibleRoutes;

    public BarterAlgorithmAlpha(List<BarterRoute> possibleRoutes) {
        this.possibleRoutes = possibleRoutes;
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
        if (level2Goods > level2GoodsTurnIn) {
            double extraSilver = (level2Goods - level2GoodsTurnIn) * BarterTier.TWO.getValue();
            barterPlan.addProfit(extraSilver);
        }

        // Figure fourth barter
        BarterRoute level4Route = findBarterRoute(BarterTier.FOUR);
        double maxLevel4Exchanges = level3Goods / level4Route.getAcceptAmount();
        double exchanges4;
        if (maxLevel4Exchanges <= level4Route.getExchanges()) {
            exchanges4 = maxLevel4Exchanges;
        } else {
            exchanges4 = level4Route.getExchanges();
        }
        double level4Goods = level4Route.getExchangeAmount() * exchanges4;
        double level3GoodsTurnIn = exchanges4 * level4Route.getAcceptAmount();
        description = "Perform " + exchanges4 + " exchanges with " + level3GoodsTurnIn +
                " Tier " + level4Route.getAcceptTier() + " goods to receive " + level4Goods +" Tier " +
                level4Route.getExchangeTier() + " goods.";
        barterPlan.addRoute(new PlannedRoute(description, exchanges));
        if (level3Goods > level3GoodsTurnIn) {
            double extraSilver = (level3Goods - level3GoodsTurnIn) * BarterTier.THREE.getValue();
            barterPlan.addProfit(extraSilver);
        }

        // Figure fifth barter
        BarterRoute level5Route = findBarterRoute(BarterTier.FIVE);
        double maxLevel5Exchanges = level4Goods / level5Route.getAcceptAmount();
        double exchanges5;
        if (maxLevel5Exchanges <= level5Route.getExchanges()) {
            exchanges5 = maxLevel5Exchanges;
        } else {
            exchanges5 = level5Route.getExchanges();
        }
        double level5Goods = level5Route.getExchangeAmount() * exchanges5;
        double level4GoodsTurnIn = exchanges5 * level5Route.getAcceptAmount();
        description = "Perform " + exchanges5 + " exchanges with " + level4GoodsTurnIn +
                " Tier " + level5Route.getAcceptTier() + " goods to receive " + level5Goods +" Tier " +
                level5Route.getExchangeTier() + " goods.";
        barterPlan.addRoute(new PlannedRoute(description, exchanges));
        if (level4Goods > level4GoodsTurnIn) {
            double extraSilver = (level4Goods - level4GoodsTurnIn) * BarterTier.FOUR.getValue();
            barterPlan.addProfit(extraSilver);
        }

        // crow coins
        BarterRoute coinBarter = findBarterRoute(BarterTier.CROWCOIN);
        double maxLevel6Exchanges = level5Goods / coinBarter.getAcceptAmount();
        double exchanges6;
        if (maxLevel6Exchanges <= coinBarter.getExchanges()) {
            exchanges6 = maxLevel6Exchanges;
        } else {
            exchanges6 = coinBarter.getExchanges();
        }
        double level6Goods = coinBarter.getExchangeAmount() * exchanges6;
        double level5GoodsTurnIn = exchanges6 * coinBarter.getAcceptAmount();
        description = "Perform " + exchanges6 + " exchanges with " + level5GoodsTurnIn +
                " Tier " + coinBarter.getAcceptTier() + " goods to receive " + level6Goods +" Tier " +
                coinBarter.getExchangeTier() + " goods.";
        barterPlan.addRoute(new PlannedRoute(description, exchanges));
        if (level5Goods > level5GoodsTurnIn) {
            double extraSilver = (level5Goods - level5GoodsTurnIn) * BarterTier.FIVE.getValue();
            barterPlan.addProfit(extraSilver);
        }

        //add crow coins
        double crowCoinsValue = (BarterTier.CROWCOIN.getValue() * level6Goods);
        barterPlan.addProfit(crowCoinsValue);


        return barterPlan;
    }

    private BarterRoute findBarterRoute(BarterTier exchangeTier) throws AlgorithmException {
        return possibleRoutes.stream().filter(route -> route.getExchangeTier().equals(exchangeTier)).findFirst().orElseThrow(AlgorithmException::new);
    }




}
