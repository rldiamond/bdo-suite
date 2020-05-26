package module.barter.storage;

import module.common.model.BdoItem;

public class StorageItem extends BdoItem {

    private int amount;

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
