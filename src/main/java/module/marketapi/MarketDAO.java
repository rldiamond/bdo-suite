package module.marketapi;

import module.marketapi.model.MarketResponse;

/**
 * Data access object for performing GET REST calls to API to obtain Marketplace information
 * on specific items.
 */
public class MarketDAO {

    private static final MarketDAO SINGLETON = new MarketDAO();

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
    public long getMarketValue(long id) {
        return fetchData(id).getPricePerOne();
    }

    public long getMarketAvailability(long id) {
        return fetchData(id).getCount();
    }

    public String getName(long id) {
        return fetchData(id).getName();
    }

}
