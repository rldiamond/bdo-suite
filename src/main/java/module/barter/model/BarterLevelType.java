package module.barter.model;

/**
 * Mapping of barter good tiers to their weight and value.
 */
public enum BarterLevelType {

    //TODO: Make these all configurable
    ZERO(0, 0), ONE(800, 0), TWO(800, 0), THREE(900, 1000000), FOUR(1000, 2000000), FIVE(1000, 5000000), CROWCOIN(0, 90000);

    private final int weight;
    private final double value;

    /**
     * Default constructor.
     *
     * @param weight The weight of the good.
     * @param value  The value of the good.
     */
    BarterLevelType(int weight, double value) {
        this.weight = weight;
        this.value = value;
    }

    /**
     * @return The weight of the good.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @return The value of the good.
     */
    public double getValue() {
        return value;
    }
}
