package module.marketapi.algorithms;

import common.algorithm.Algorithm;
import common.algorithm.AlgorithmException;
import module.barter.algorithms.MostValuableCoinItemAlgorithm;
import module.marketapi.model.CrowCoinVendorItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        MostValuableCoinItemAlgorithm mostValuableCoinItemAlgorithm = new MostValuableCoinItemAlgorithm();
        CrowCoinVendorItem item = mostValuableCoinItemAlgorithm.run();
        return calculateCoinValueForItem(item);
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
