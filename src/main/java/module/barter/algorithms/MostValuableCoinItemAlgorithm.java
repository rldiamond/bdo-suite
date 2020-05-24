package module.barter.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.marketapi.model.CrowCoinVendorItem;

import java.util.ArrayList;
import java.util.List;

public class MostValuableCoinItemAlgorithm implements Algorithm<CrowCoinVendorItem> {
    
    @Override
    public CrowCoinVendorItem run() throws AlgorithmException {

        List<CrowCoinVendorItem> crowCoinVendorItems = new ArrayList<>();
        crowCoinVendorItems.add(CrowCoinVendorItem.buildWithName("Caphras Stone", 30));
        crowCoinVendorItems.add(CrowCoinVendorItem.buildWithName("Manos Ring", 2500));
        crowCoinVendorItems.add(CrowCoinVendorItem.buildWithName("Manos Earring", 2500));
        crowCoinVendorItems.add(CrowCoinVendorItem.buildWithName("Manos Belt", 2500));
        crowCoinVendorItems.add(CrowCoinVendorItem.buildWithName("Manos Necklace", 2500));

        CrowCoinVendorItem mostValuableItem = null;

        for (CrowCoinVendorItem item : crowCoinVendorItems) {
            if (mostValuableItem == null) {
                mostValuableItem = item;
            } else if (calculateCoinValueForItem(mostValuableItem) < calculateCoinValueForItem(item)) {
                mostValuableItem = item;
            }
        }

        return mostValuableItem;
    }

    /**
     * Calculate the value of a single Crow Coin based on the provided vendor item.
     *
     * @param item The item to calculate the value of a single Crow Coin for.
     * @return The double value of a single Crow Coin of the provided item is purchased.
     */
    private double calculateCoinValueForItem(CrowCoinVendorItem item) {
        return item.getItemValue() / item.getCrowCoins();
    }
}
