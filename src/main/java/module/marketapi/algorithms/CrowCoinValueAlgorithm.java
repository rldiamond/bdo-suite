package module.marketapi.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.marketapi.model.CrowCoinVendorItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * Calculates the value of a single Crow Coin based on the value of various items available from the Crow Coin vendor.
 */
public class CrowCoinValueAlgorithm implements Algorithm<Double> {

    private static final Logger logger = LogManager.getLogger(CrowCoinValueAlgorithm.class);

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

        logger.info("Mouse valuable Crow Coin item: " + mostValuableItem.getItemName());
        return calculateCoinValueForItem(mostValuableItem);
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
