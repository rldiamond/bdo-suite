package module.barter.model;

/**
 * Simple POJO used to describe each available Barter level.
 */
public class BarterLevel {

    private BarterLevelType level;
    private double weight;
    private double value;

    public BarterLevel() {

    }

    public BarterLevel(BarterLevelType level, double weight, double value) {
        this.level = level;
        this.weight = weight;
        this.value = value;
    }

    public BarterLevelType getLevel() {
        return level;
    }

    public double getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }
}
