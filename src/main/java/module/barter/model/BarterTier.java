package module.barter.model;

public enum BarterTier {

    ZERO(0,0),ONE(800,0),TWO(800,0),THREE(900,1000000),FOUR(1000,2000000),FIVE(1000,5000000),CROWCOIN(0,90000);

    private final int weight;
    private final double value;

    BarterTier(int weight, double value) {
        this.weight = weight;
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }
}
