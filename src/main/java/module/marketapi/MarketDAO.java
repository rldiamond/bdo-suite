package module.marketapi;

import common.algorithm.AlgorithmException;
import common.rest.RestClient;
import module.marketapi.algorithms.CrowCoinValueAlgorithm;
import module.marketapi.model.MarketResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Data access object for performing GET REST calls to API to obtain Marketplace information
 * on specific items.
 */
public class MarketDAO {

    private static final MarketDAO SINGLETON = new MarketDAO();
    private static final Logger logger = LogManager.getLogger(MarketDAO.class);

    /**
     * Retrieve the instance of the MarketDAO.
     *
     * @return The MarketDAO instance.
     */
    public static MarketDAO getInstance() {
        return SINGLETON;
    }

    private static final String BASE_URL = "https://omegapepega.com/na/";
    private final RestClient restClient;

    private MarketDAO() {
        restClient = RestClient.build(BASE_URL);
    }

    /**
     * Fetches all data for the supplied item by ID.
     *
     * @param id The ID of the item to retrieve data on.
     * @return MarketResponse with all data on the supplied ID.
     */
    public MarketResponse fetchData(long id) {
        return restClient.get(String.valueOf(id) + "/0", MarketResponse.class);
    }

    /**
     * Gets the current market value for item with the supplied ID.
     *
     * @param id The ID of the item to retrieve data on.
     * @return
     */
    public double getMarketValue(long id) {
        return fetchData(id).getPricePerOne();
    }

    public long getMarketAvailability(long id) {
        return fetchData(id).getCount();
    }

    public String getName(long id) {
        return fetchData(id).getName();
    }

    public Optional<MarketResponse> searchByName(String name) {
        logger.info("Searching market API for: " + name);

        String searchTerm = name.trim().replace(" ", "%20");
        MarketResponse response = null;
        try {
            response = restClient.get(searchTerm + "/0", MarketResponse.class);
        } catch (Exception ex) {
            logger.warn("Failed to find data in market API for: " + name);
        }

        if (response != null && response.getName().equalsIgnoreCase(name)) {
            logger.info("Found item: " + name);
            return Optional.of(response);
        }

        logger.warn("Failed to find data in market API for: " + name);
        return Optional.empty();
    }

    /**
     * Get the current value of a single crow coin based on the best item from the Crow Coin vendor.
     * @return
     */
    public double getCrowCoinValue() {
        CrowCoinValueAlgorithm algorithm = new CrowCoinValueAlgorithm();
        double value = 0;
        try {
            value = algorithm.run();
        } catch (AlgorithmException ex) {
            logger.error("Failed to process the CrowCoinValue algorithm!", ex);
        }
        return value;
    }

}
