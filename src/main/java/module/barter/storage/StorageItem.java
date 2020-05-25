package module.barter.storage;

public class StorageItem {

    private String name;
    private int amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void addItems(int amount) {
        this.amount += amount;
    }

    public void removeItems(int amount) {
        this.amount -= amount;
    }
}
