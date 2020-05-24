package module.barter.model;

import common.json.JsonObject;

/**
 * Model for a possible barter route.
 */
public class BarterRoute extends JsonObject {

    private String tradeItem;
    private String acceptGoodName;
    private String exchangeGoodName;
    private int exchanges;
    private int acceptAmount;
    private int exchangeAmount;
    private BarterLevelType acceptTier;
    private BarterLevelType exchangeTier;

    /**
     * Construct a full Barter Route.
     *
     * @param exchanges      The number of possible exchanges.
     * @param acceptTier     The tier of good accepted for this barter.
     * @param acceptAmount   The amount of accepted good tier for this barter.
     * @param exchangeTier   The tier of the good received for this barter.
     * @param exchangeAmount The amount of received good for this barter.
     */
    public BarterRoute(int exchanges, BarterLevelType acceptTier, int acceptAmount, BarterLevelType exchangeTier, int exchangeAmount, String tradeItem, String acceptGoodName, String exchangeGoodName) {
        this.exchanges = exchanges;
        this.acceptAmount = acceptAmount;
        this.exchangeAmount = exchangeAmount;
        this.acceptTier = acceptTier;
        this.exchangeTier = exchangeTier;
        this.tradeItem = tradeItem;
        this.acceptGoodName = acceptGoodName;
        this.exchangeGoodName = exchangeGoodName;
    }

    public BarterRoute() {

    }

    public void setTradeItem(String tradeItem) {
        this.tradeItem = tradeItem;
    }

    public void setAcceptGoodName(String acceptGoodName) {
        this.acceptGoodName = acceptGoodName;
    }

    public void setExchangeGoodName(String exchangeGoodName) {
        this.exchangeGoodName = exchangeGoodName;
    }

    public void setExchanges(int exchanges) {
        this.exchanges = exchanges;
    }

    public void setAcceptAmount(int acceptAmount) {
        this.acceptAmount = acceptAmount;
    }

    public void setExchangeAmount(int exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public void setAcceptTier(BarterLevelType acceptTier) {
        this.acceptTier = acceptTier;
    }

    public void setExchangeTier(BarterLevelType exchangeTier) {
        this.exchangeTier = exchangeTier;
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
    public BarterLevelType getAcceptTier() {
        return acceptTier;
    }

    /**
     * @return The tier of good received from an exchange.
     */
    public BarterLevelType getExchangeTier() {
        return exchangeTier;
    }

    public String getTradeItem() {
        return tradeItem;
    }

    public String getAcceptGoodName() {
        return acceptGoodName;
    }

    public String getExchangeGoodName() {
        return exchangeGoodName;
    }


}
