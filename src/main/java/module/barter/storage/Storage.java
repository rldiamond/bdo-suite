package module.barter.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: Account for items that DO NOT stack
public class Storage {

    private int capacity;
    private int used;
    private List<StorageItem> storedItems = new ArrayList<>();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public void addItem(String name, int amount) {
        StorageItem item = getItem(name).orElseGet(() -> {
            StorageItem storageItem = new StorageItem();
            storageItem.setName(name);
            storedItems.add(storageItem);
            return storageItem;
        });

        item.addItems(amount);
    }

    public void removeItem(String name, int amount) {
        getItem(name).ifPresent(storageItem -> {
            storageItem.removeItems(amount);
            if (storageItem.getAmount() <= 0) {
                storedItems.remove(storageItem);
            }
        });
    }

    public int getCapacity() {
        return capacity;
    }

    public int getUsed() {
        return used;
    }

    public int getRemainingSpace() {
        return capacity - used;
    }

    public List<StorageItem> getStoredItems() {
        return storedItems;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean hasItem(String name) {
        return storedItems.stream().anyMatch(storageItem -> name.equalsIgnoreCase(storageItem.getName()));
    }

    public Optional<StorageItem> getItem(String name) {
        return storedItems.stream().filter(storageItem -> name.equalsIgnoreCase(storageItem.getName())).findAny();
    }
}
