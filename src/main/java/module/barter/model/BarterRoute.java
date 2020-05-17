package module.barter.model;

import common.json.JsonObject;

public class BarterRoute implements JsonObject {
    private final int exchanges;
    private final int acceptAmount;
    private final int exchangeAmount;
    private final BarterTier acceptTier;
    private final BarterTier exchangeTier;

    public BarterRoute(int exchanges, BarterTier acceptTier, int acceptAmount, BarterTier exchangeTier, int exchangeAmount) {
        this.exchanges = exchanges;
        this.acceptAmount = acceptAmount;
        this.exchangeAmount = exchangeAmount;
        this.acceptTier = acceptTier;
        this.exchangeTier = exchangeTier;
    }

    public int getExchanges() {
        return exchanges;
    }

    public int getAcceptAmount() {
        return acceptAmount;
    }

    public int getExchangeAmount() {
        return exchangeAmount;
    }

    public BarterTier getAcceptTier() {
        return acceptTier;
    }

    public BarterTier getExchangeTier() {
        return exchangeTier;
    }
}
