package module.barter.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.barter.model.CrowCoinVendorItem;
import module.marketapi.MarketDAO;

import java.util.ArrayList;
import java.util.List;

public class CrowCoinValueAlgorithm implements Algorithm<Double> {

    /**
     * @inheritDoc
     */
    @Override
    public Double run() throws AlgorithmException {

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

        System.out.println("Most valuable Crow Coin item: " + mostValuableItem.getItemName());
        return calculateCoinValueForItem(mostValuableItem);
    }

    private double calculateCoinValueForItem(CrowCoinVendorItem item) {
        return item.getItemValue() / item.getCrowCoins();
    }
}
