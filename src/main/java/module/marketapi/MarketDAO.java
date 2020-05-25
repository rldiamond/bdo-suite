package module.marketapi;

import common.algorithm.AlgorithmException;
import common.rest.RestClient;
import javafx.scene.image.Image;
import module.marketapi.algorithms.CrowCoinValueAlgorithm;
import module.marketapi.model.MarketResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Data access object for performing GET REST calls to API to obtain Marketplace information
 * on specific items.
 */
public class MarketDAO {

    private static final MarketDAO SINGLETON = new MarketDAO();
    private static final Logger logger = LogManager.getLogger(MarketDAO.class);
    private static final MarketCache cache = MarketCache.getInstance();

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
        logger.info("Fetching data for " + id);
        return cache.get(id).orElseGet(() -> {
            final int retries = 3;
            MarketResponse response = null;
            int attempt = 0;
            while (response == null) {
                try {
                    response = restClient.get(String.valueOf(id) + "/0", MarketResponse.class);
                    cache.add(response);
                } catch (Exception ex) {
                    attempt++;
                    logger.warn("Could not fetch data for: " + id + ". Attempt " + attempt + "/" + retries);
                    if (attempt == retries) {
                        logger.error("Failed to retrieve data for: " +id + " after retrying several times!");
                        response = new MarketResponse();
                    } else {
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            logger.error(e);
                            break;
                        }
                    }
                }
            }
            return response;
        });
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
        // check cache
        MarketResponse r = cache.get(name).orElseGet(() -> {
            String searchTerm = name.trim().replace(" ", "%20");
            MarketResponse response = null;
            try {
                logger.info("Searching market API for: " + name);
                response = restClient.get(searchTerm + "/0", MarketResponse.class);
            } catch (Exception ex) {
                logger.warn("Failed to find data in market API for: " + name);
            }
            if (response != null && response.getName().equalsIgnoreCase(name)) {
                logger.info("Found item: " + name);
                cache.add(response);
                return response;
            }
            return null;
        });

        return Optional.ofNullable(r);

    }

    public Image getItemImage(long id) {
        Image image = null;
        try {
            image = new Image("/module/barter/images/" + id + ".png");
        } catch (Exception ex) {
            logger.warn("Could not find image for item with ID: " + id);
        }
        return image;
    }

    /**
     * Get the current value of a single crow coin based on the best item from the Crow Coin vendor.
     *
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
