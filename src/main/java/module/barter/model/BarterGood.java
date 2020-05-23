package module.barter.model;

/**
 * POJO to describe a single barter good.
 */
public class BarterGood {

    private String name;
    private BarterLevelType level;
    private long itemId;

    public BarterGood() {
    }

    public BarterGood(String name, BarterLevelType level, long itemId) {
        this.name = name;
        this.level = level;
        this.itemId = itemId;
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

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
