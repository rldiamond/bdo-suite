package module.barter.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import common.logging.AppLogger;
import common.utilities.FileUtil;
import module.barter.model.*;
import module.barter.storage.Storage;
import module.marketapi.MarketDAO;
import module.marketapi.algorithms.CrowCoinValueAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BarterAlgorithm implements Algorithm<BarterPlan> {

    private static final double MAX_PARLEY = 1000000;
    private static final double VP_PARLEY_DISCOUNT_RATE = 0.10;
    private final List<Barter> barters;
    private PlayerStorageLocations storageLocations;
    private List<Barter> completedBarters;

    private final AppLogger logger = AppLogger.getLogger();

    private BarterPlan barterPlan;

    public BarterAlgorithm(List<Barter> barters) {
        this.barters = barters;
    }

    @Override
    public BarterPlan run() throws AlgorithmException {
        barterPlan = new BarterPlan();
        completedBarters = new ArrayList<>();
        storageLocations = FileUtil.loadModuleData(PlayerStorageLocations.class);

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
        Barter secondBarter = null;
        try {
            secondBarter = findBarterAcceptingGood(firstBarter.getExchangeGoodName()).orElseThrow(AlgorithmException::new);
        } catch (Exception ex) {
            //level 5 item is in storage..
            if (firstBarter.getExchangeGoodName().equalsIgnoreCase("Crow Coin")){
                int neededItems = firstBarter.getAcceptAmount() * firstBarter.getExchanges();
                if (isItemInStorage(firstBarter.getAcceptGoodName(), neededItems)) {
                    // we have the item in storage, so instead of the below, we go to storage to get it.
                    //TODO: examine # of each in each storage..
                    List<StorageLocation> locationsWithGood = getStorageLocationsWithItem(firstBarter.getAcceptGoodName());
                    PlannedRoute storageRoute = new StoragePlannedRoute(locationsWithGood.get(0));
                    storageRoute.setReceivedGood(BarterGood.getBarterGoodByName(firstBarter.getAcceptGoodName()).orElseThrow(AlgorithmException::new));
                    storageRoute.setReceivedAmount(neededItems);
                    plannedRoutes.add(storageRoute);
                }
                PlannedRoute coinRoute = new PlannedRoute();
                coinRoute.setExchanges(firstBarter.getExchanges());
                coinRoute.setReceivedAmount(firstBarter.getExchangeAmount());
                coinRoute.setReceivedGood(BarterGood.getBarterGoodByName(firstBarter.getExchangeGoodName()).get());
                coinRoute.setTurnInGood(BarterGood.getBarterGoodByName(firstBarter.getAcceptGoodName()).get());
                coinRoute.setTurnInAmount(firstBarter.getAcceptAmount());
                plannedRoutes.add(coinRoute);
                return plannedRoutes;
            }
        }


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

        //check if its in storage
        int neededItems = firstBarter.getAcceptAmount() * (int) exchanges;
        if (isItemInStorage(firstBarter.getAcceptGoodName(), neededItems)) {
            // we have the item in storage, so instead of the below, we go to storage to get it.
            //TODO: examine # of each in each storage..
            List<StorageLocation> locationsWithGood = getStorageLocationsWithItem(firstBarter.getAcceptGoodName());
            PlannedRoute storageRoute = new StoragePlannedRoute(locationsWithGood.get(0));
            storageRoute.setReceivedGood(BarterGood.getBarterGoodByName(firstBarter.getAcceptGoodName()).orElseThrow(AlgorithmException::new));
            storageRoute.setReceivedAmount(neededItems);
            plannedRoutes.add(storageRoute);
        }

        if (route.getTurnInGood() != null & route.getTurnInGood().getLevel().equals(BarterLevelType.ZERO)) {
            // check if there is enough on the market
            long available = MarketDAO.getInstance().getMarketAvailability(route.getTurnInGood().getItemId());
            if (available < (exchanges*firstBarter.getAcceptAmount())){
                //not enough
                return Collections.emptyList();
            }
        }

        route.setExchanges((int) exchanges);
        route.setTurnInAmount((int) (firstBarter.getAcceptAmount() * exchanges));
        route.setReceivedAmount((int) (exchanges * firstBarter.getExchangeAmount()));
        barterPlan.addParley(calculateParley(firstBarter.getParley(), (int) exchanges));
        if (route.getTurnInGood().getLevel().equals(BarterLevelType.ZERO)) {
            //trade good, calculate cost
            try {
                double cost = MarketDAO.getInstance().getMarketValue(route.getTurnInGood().getItemId()) * route.getTurnInAmount();
                barterPlan.addProfit(0 -cost);
            } catch (Exception ex) {
                //
            }
        }
        //barterPlan.addRoute(route);
        plannedRoutes.add(route);
        this.completedBarters.add(firstBarter);

        double currentShipWeight = 0;

        Barter currentBarter = firstBarter;
        PlannedRoute previousRoute = route;
        while (findNextBarter(currentBarter).isPresent()) {
            PlannedRoute newRoute = createRouteFromPrevious(previousRoute);
            plannedRoutes.add(newRoute);
            currentBarter = findNextBarter(currentBarter).get();
            previousRoute = newRoute;
        }


        return plannedRoutes;
    }

    private Optional<Barter> findNextBarter(Barter barter) {
        return findBarterAcceptingGood(barter.getExchangeGoodName());
    }

    private PlannedRoute createRouteFromPrevious(PlannedRoute previousRoute) throws AlgorithmException {
        double currentShipWeight = 0; //TODO: pass in..
        final int shipCapacity = BarterSettings.getSettings().getShipWeightCapacity();

        //find the next barter.
        Barter barter = findBarterAcceptingGood(previousRoute.getReceivedGood().getName()).orElseThrow(AlgorithmException::new);

        double optimalAcceptedGoods = barter.getAcceptAmount() * barter.getExchanges();
        double exchanges;
        if (optimalAcceptedGoods <= previousRoute.getReceivedAmount()) {
            exchanges = optimalAcceptedGoods / barter.getAcceptAmount();
        } else {
            exchanges = (double) previousRoute.getReceivedAmount() / barter.getAcceptAmount();
        }

        //ship weight stuff
        double weightOfHandedInGood = BarterGood.getBarterGoodByName(barter.getAcceptGoodName()).get().getBarterLevel().getWeight();
        double weightOfReceivedGood = BarterGood.getBarterGoodByName(barter.getExchangeGoodName()).get().getBarterLevel().getWeight();
        double weightLoss = exchanges * weightOfHandedInGood;
        double weightGain = exchanges * weightOfReceivedGood;
        if (shipCapacity  >= (currentShipWeight - weightLoss - weightGain)) {
            //ship can hold all

        } else {
            //ship is overweight.. unload some cargo.. in a for loop
            double weightOfOneExchange = weightOfReceivedGood * barter.getExchangeAmount();
            for (int i = 0; i<= (int) exchanges; i++) {
                if (shipCapacity >= currentShipWeight + weightOfOneExchange) {
                    // add a storage run.
                }
            }


        }


        PlannedRoute route = new PlannedRoute();
        route.setTurnInGood(previousRoute.getReceivedGood());
        route.setReceivedGood(BarterGood.getBarterGoodByName(barter.getExchangeGoodName()).orElseThrow(AlgorithmException::new));

        route.setExchanges((int) exchanges);
        route.setTurnInAmount((int) (barter.getAcceptAmount() * exchanges));
        route.setReceivedAmount((int) (exchanges * barter.getExchangeAmount()));
        barterPlan.addParley(calculateParley(barter.getParley(), (int) exchanges));
        completedBarters.add(barter);

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
        // see if the item is in storage..
        int acceptRequired = barter.getAcceptAmount() * barter.getExchanges();
        if (isItemInStorage(barter.getAcceptGoodName(), acceptRequired)) {
            return barter;
        }
        // find previous barter
        if (findBarterExchangingGood(barter.getAcceptGoodName()).isPresent()) {
            Barter previousBarter = findBarterExchangingGood(barter.getAcceptGoodName()).get();
            if (completedBarters.contains(previousBarter)) {
                return barter;
            }
            return findFirstBarterInChain(previousBarter);
        } else {
            return barter;
        }
    }

    private boolean isItemInStorage(String goodName) {
        return storageLocations.getStorageLocations().stream().map(StorageLocation::getStorage).anyMatch(storage -> storage.hasItem(goodName));
    }

    private boolean isItemInStorage(String goodName, int amount) {
        List<Storage> storagesWithItem = storageLocations.getStorageLocations().stream().map(StorageLocation::getStorage).filter(storage -> storage.hasItem(goodName))
                .collect(Collectors.toList());

        int items = 0;

        for (Storage storage : storagesWithItem) {
            items += storage.getItem(goodName).get().getAmount();
        }

        return items >= amount;
    }

    private List<StorageLocation> getStorageLocationsWithItem(String goodName) {
        return storageLocations.getStorageLocations().stream().filter(storageLocation -> storageLocation.getStorage().hasItem(goodName)).collect(Collectors.toList());
    }

    /**
     * Calculate the total parley required based on the number of exchanges to perform,
     * the required parley per exchange, taking into account if the player has a value pack.
     *
     * @param requiredParley The amount of parley required for one exchange.
     * @param exchanges      The amount of exchanges to calculate total parley for.
     * @return Total parley cost to perform said exchanges.
     */
    private int calculateParley(double requiredParley, int exchanges) {
        // User gets a parley discount if they have an active value pack.
        if (BarterSettings.getSettings().hasValuePack()) {
            requiredParley = requiredParley - (requiredParley * VP_PARLEY_DISCOUNT_RATE);
        }
        return (int) requiredParley * exchanges;
    }

    private List<Barter> findBartersWithExchangeTier(BarterLevelType levelType) {
        return barters.stream().filter(barter -> {
            return BarterGood.getBarterGoodByName(barter.getExchangeGoodName()).get().getLevel().equals(levelType);
        }).filter(barter -> !completedBarters.contains(barter)).collect(Collectors.toList());
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
