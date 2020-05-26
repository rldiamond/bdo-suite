package module.barter.model;

import module.barter.storage.Storage;

public class StorageLocation extends Location {

    private final Storage storage;

    public StorageLocation(String name, int storageCapacity) {
        this.storage = new Storage(storageCapacity);
    }

    public Storage getStorage() {
        return storage;
    }
}
