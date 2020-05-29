package module.barter.model;

/**
 * Mapping of barter good tiers to their weight and value.
 */
public enum BarterLevelType {

    ZERO(6), ONE(5), TWO(4), THREE(3), FOUR(2), FIVE(1), CROW_COIN(0);

    private final int rarity;

    private BarterLevelType(int rarity) {
        this.rarity = rarity;
    }

    public int getRarity() {
        return rarity;
    }
}
