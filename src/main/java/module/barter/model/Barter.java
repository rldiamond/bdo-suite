package module.barter.model;

/**
 * Model for a possible barter route.
 */
public class Barter {

    private String tradeItem;
    private String acceptGoodName;
    private String exchangeGoodName;
    private int exchanges;
    private int acceptAmount;
    private int exchangeAmount;
    private int parley;

    public Barter() {

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

    public String getTradeItem() {
        return tradeItem;
    }

    public String getAcceptGoodName() {
        return acceptGoodName;
    }

    public String getExchangeGoodName() {
        return exchangeGoodName;
    }

    public int getParley() {
        return parley;
    }

    public void setParley(int parley) {
        this.parley = parley;
    }
}
