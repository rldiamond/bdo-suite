package module.barter.model;

/**
 * POJO to describe a single barter good.
 */
public class BarterGood {

    private String name;
    private BarterLevelType level;

    public BarterGood() {
    }

    public BarterGood(String name, BarterLevelType level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BarterLevelType getLevel() {
        return level;
    }

    public void setLevel(BarterLevelType level) {
        this.level = level;
    }
}
