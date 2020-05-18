package module.barter.model;

import common.json.JsonObject;

/**
 * Model for a possible barter route.
 */
public class BarterRoute extends JsonObject {

    private final String tradeItem;
    private final int exchanges;
    private final int acceptAmount;
    private final int exchangeAmount;
    private final BarterTier acceptTier;
    private final BarterTier exchangeTier;

    /**
     * Construct a full Barter Route.
     *
     * @param exchanges      The number of possible exchanges.
     * @param acceptTier     The tier of good accepted for this barter.
     * @param acceptAmount   The amount of accepted good tier for this barter.
     * @param exchangeTier   The tier of the good received for this barter.
     * @param exchangeAmount The amount of received good for this barter.
     */
    public BarterRoute(int exchanges, BarterTier acceptTier, int acceptAmount, BarterTier exchangeTier, int exchangeAmount, String tradeItem) {
        this.exchanges = exchanges;
        this.acceptAmount = acceptAmount;
        this.exchangeAmount = exchangeAmount;
        this.acceptTier = acceptTier;
        this.exchangeTier = exchangeTier;
        this.tradeItem = tradeItem;
    }

    /**
     * @return The number of possible exchanges for this barter.
     */
    public int getExchanges() {
        return exchanges;
    }

    /**
     * @return The amount of accepted goods required for one exchange.
     */
    public int getAcceptAmount() {
        return acceptAmount;
    }

    /**
     * @return The amount of recieved goods for one exchange.
     */
    public int getExchangeAmount() {
        return exchangeAmount;
    }

    /**
     * @return The tier of good accepted for an exchange.
     */
    public BarterTier getAcceptTier() {
        return acceptTier;
    }

    /**
     * @return The tier of good received from an exchange.
     */
    public BarterTier getExchangeTier() {
        return exchangeTier;
    }

    public String getTradeItem() {
        return tradeItem;
    }
}
